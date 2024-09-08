package com.harrontech.vector_db_demo.dto

import com.harrontech.vector_db_demo.Product

data class MergeProductRequest(
    val title: String,
    val description: String,
    val type: String = "Fruit"
) {
    fun mapTo() : Product {
        return Product(
            title = this.title,
            description = this.description
        )
    }

    fun mapTo(id: String) : Product {
        return Product(
            id = id,
            title = this.title,
            description = this.description
        )
    }
}