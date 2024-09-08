package com.harrontech.vector_db_demo.v1

import com.harrontech.vector_db_demo.Product
import com.harrontech.vector_db_demo.ProductRepository
import com.harrontech.vector_db_demo.ProductsService
import com.harrontech.vector_db_demo.dto.MergeProductRequest
import com.harrontech.vector_db_demo.dto.QueryRequest
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ProductsServiceV1(val repository: ProductRepository): ProductsService {
    override fun findById(id: String): Product {
        return repository.findById(id).orElseThrow { Exception("Not found") }
    }

    override fun getByQuery(query: QueryRequest): List<Product> {
        val pageRequest = PageRequest.of(query.page, query.size)

        if (query.query != null) {
            return repository.findByDescriptionLike(query.query, pageRequest)
        }
        return repository.findAll(pageRequest).content
    }

    override fun getByQueryWithPoints(query: QueryRequest): List<Pair<Product, Float>> {
        TODO("Not yet implemented")
    }

    override fun create(request: MergeProductRequest): Product {
        val product = request.mapTo()
        return repository.save(product)
    }

    override fun update(id: String, request: MergeProductRequest): Product {
        val product = request.mapTo(id)
        return repository.save(product)
    }

    override fun deleteById(id: String) {
        repository.deleteById(id)
    }

    override fun deleteAll() {
        repository.deleteAll()
    }
}