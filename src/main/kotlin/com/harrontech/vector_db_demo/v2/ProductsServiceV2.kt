package com.harrontech.vector_db_demo.v2

import com.harrontech.vector_db_demo.Product
import com.harrontech.vector_db_demo.ProductRepository
import com.harrontech.vector_db_demo.ProductsService
import com.harrontech.vector_db_demo.dto.MergeProductRequest
import com.harrontech.vector_db_demo.dto.QueryRequest
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.filter.Filter
import org.springframework.ai.vectorstore.filter.Filter.Key
import org.springframework.ai.vectorstore.filter.Filter.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service


@Service
class ProductsServiceV2(val vectorStore: VectorStore, val repository: ProductRepository): ProductsService {

    override fun findById(id: String): Product {
        val vector = vectorStore.similaritySearch(SearchRequest
            .defaults()
            .withTopK(1)
            .withFilterExpression(Filter.Expression(Filter.ExpressionType.EQ, Key("id"), Value(id))))
            .first()

        return repository.findById(vector.id).orElseThrow { Exception("Not found") }
    }

    override fun getByQuery(query: QueryRequest): List<Product> {
        TODO("Not yet implemented")
    }

    override fun getByQueryWithPoints(query: QueryRequest): List<Pair<Product, Float>> {
        val pageRequest = PageRequest.of(query.page, query.size)

        if (query.query != null) {
            val results: List<Pair<String, Float>> = vectorStore.similaritySearch(SearchRequest
                    .query(query.query)
                    .withTopK(query.size * query.page + query.size))
                .map { Pair(it.id, it.metadata["distance"] as Float) }

            return repository.findAllByIdIn(results.map { it.first }, pageRequest)
                .zip(results.map { it.second })
                .map { Pair(it.first, it.second) }
        }

        val ids = vectorStore.similaritySearch(SearchRequest.defaults()
            .withTopK(query.size * query.page + query.size))
            .map { it.id }

        return repository.findAllByIdIn(ids, pageRequest)
            .map { Pair(it, -1f) }
    }

    override fun create(request: MergeProductRequest): Product {
        val product = request.mapTo()

        val document = Document(product.id, product.description, mapOf(Pair("title", product.title),
            Pair("doc_content", product.description)))
        vectorStore.add(listOf(document))


        return repository.save(product)
    }

    override fun update(id: String, request: MergeProductRequest): Product {
        deleteById(id)
        return create(request)
    }

    override fun deleteById(id: String) {
        vectorStore.delete(listOf(id))
        repository.deleteById(id)
    }

    override fun deleteAll() {
        val ids = vectorStore.similaritySearch(SearchRequest.query(""))
            .map { it.id }

        vectorStore.delete(ids)
        repository.deleteAll()
    }
}