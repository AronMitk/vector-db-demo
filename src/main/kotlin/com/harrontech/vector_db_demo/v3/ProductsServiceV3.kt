package com.harrontech.vector_db_demo.v3

import com.harrontech.vector_db_demo.Product
import com.harrontech.vector_db_demo.ProductRepository
import com.harrontech.vector_db_demo.ProductsService
import com.harrontech.vector_db_demo.dto.MergeProductRequest
import com.harrontech.vector_db_demo.dto.QueryRequest
import io.qdrant.client.ConditionFactory.hasId
import io.qdrant.client.PointIdFactory.id
import io.qdrant.client.QdrantClient
import io.qdrant.client.ValueFactory.value
import io.qdrant.client.VectorsFactory.vectors
import io.qdrant.client.WithPayloadSelectorFactory.enable
import io.qdrant.client.grpc.Points
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*


//https://docs.spring.io/spring-ai/reference/api/embeddings/onnx.html
//https://qdrant.tech/documentation/frameworks/spring-ai/
//https://qdrant.tech/documentation/quick-start/

////https://cookbook.openai.com/examples/vector_databases/qdrant/using_qdrant_for_embeddings_search


@Service
class ProductsServiceV3(val client: QdrantClient, val service: EmbeddingService, val repository: ProductRepository): ProductsService {


    override fun findById(id: String): Product {
        var idW = id(UUID.fromString(id))
        val obj = client.searchAsync(
            Points.SearchPoints.newBuilder()
                .setCollectionName("products_collection")
                .setLimit(3)
                .setFilter(Points.Filter.newBuilder().addMust(hasId(idW)))
                .setWithPayload(enable(true))
                .build())
            .get().first()

        return repository.findById(obj.id.uuid.toString()).orElseThrow { Exception("Not found") }
    }

    override fun getByQuery(query: QueryRequest): List<Product> {
        TODO("Not yet implemented")
    }

    private fun getAll(): Points.ScrollResponse {
        val searchResult = client.scrollAsync(
            Points.ScrollPoints.newBuilder()
                .setCollectionName("products_collection")
                .setWithPayload(enable(true))
                .build())
            .get()
        return searchResult
    }

    override fun getByQueryWithPoints(query: QueryRequest): List<Pair<Product, Float>> {
        val pageRequest = PageRequest.of(query.page, query.size)

        if (query.query != null) {
            val embeddings: List<Float> = service.createEmbedding(query.query)

            val resultsIds = client
                .searchAsync(
                    Points.SearchPoints.newBuilder()
                        .setCollectionName("products_collection")
                        .addAllVector(embeddings)
                        .setOffset((query.page * query.size).toLong())
                        .setLimit(query.size.toLong())
                        .build()
                )
                .get()

            var map = resultsIds.map { Pair(it.id.uuid.toString(), it.score) }

            return repository.findAllByIdIn(map.map { it.first }, pageRequest)
                .zip(map.map { it.second })
                .map { Pair(it.first, it.second) }
        }

        return repository.findAllByIdIn(getAll().resultList.map { it.id.uuid.toString() }, pageRequest)
            .map { Pair(it, -1f) }
    }

    override fun create(request: MergeProductRequest): Product {
        val productReq = request.mapTo()

        val embeddings: Points.Vectors? = vectors(service.createEmbedding(productReq.description))
        var points: List<Points.PointStruct> = listOf(
            Points.PointStruct.newBuilder()
                .setId(id(UUID.fromString(productReq.id)))
                .setVectors(embeddings)
                .putAllPayload(mapOf(Pair("title", value(productReq.title)),
                    Pair("doc_content", value(productReq.description))))
                .build()
        )

        client.upsertAsync("products_collection", points).get()

        return repository.save(productReq)
    }

    override fun update(id: String, request: MergeProductRequest): Product {
        deleteById(id)
        return create(request)
    }

    override fun deleteById(id: String) {
        client.deleteAsync("products_collection", Points.Filter
            .newBuilder()
            .addMust(hasId(id(UUID.fromString(id))))
            .build()
        )

        repository.deleteById(id)

    }

    override fun deleteAll() {
        val points = getAll()
        client.deleteAsync("products_collection", points.resultList.map { it.id })

        repository.deleteAll()
    }

}