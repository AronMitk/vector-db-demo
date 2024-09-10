package com.harrontech.vector_db_demo.dto

import com.harrontech.vector_db_demo.Product

data class GetPagedProductResponse(
    val products: List<GetProductResponse>,
    val page: Int,
    val size: Int,
    val total: Int
) {
    companion object {
        fun mapFrom(products: List<Product>, page: Int, size: Int): GetPagedProductResponse {
            return GetPagedProductResponse(
                products.map { GetProductResponse.mapFrom(it) },
                page,
                size,
                products.size
            )
        }

        fun mapFromWithPoints(products: List<Pair<Product, Float>>, page: Int, size: Int): GetPagedProductResponse {
            return GetPagedProductResponse(
                products.map { GetProductResponse.mapFrom(it.first, it.second) },
                page,
                size,
                products.size
            )
        }
    }
}