package com.harrontech.vector_db_demo

import com.harrontech.vector_db_demo.dto.MergeProductRequest
import com.harrontech.vector_db_demo.dto.QueryRequest

interface ProductsService {
    fun findById(id: String): Product

    fun getByQuery(query: QueryRequest): List<Product>

    fun getByQueryWithPoints(query: QueryRequest): List<Pair<Product, Float>>

    fun create(request: MergeProductRequest): Product

    fun update(id: String, request: MergeProductRequest): Product

    fun deleteById(id: String)

    fun deleteAll()
}