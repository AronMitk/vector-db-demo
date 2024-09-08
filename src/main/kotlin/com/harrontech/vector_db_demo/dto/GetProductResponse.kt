package com.harrontech.vector_db_demo.dto

import com.harrontech.vector_db_demo.Product

data class GetProductResponse(
    val id: String,
    val title: String,
    val description: String,
    val points: Float = -1f
) {
    companion object {
        fun mapFrom(product: Product): GetProductResponse {
            return GetProductResponse(
                id = product.id,
                title = product.title,
                description = product.description
            )
        }

        fun mapFrom(product: Product, points: Float): GetProductResponse {
            return GetProductResponse(
                id = product.id,
                title = product.title,
                description = product.description,
                points = points
            )
        }
    }
}