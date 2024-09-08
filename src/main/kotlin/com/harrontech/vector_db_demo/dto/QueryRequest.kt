package com.harrontech.vector_db_demo.dto

data class QueryRequest(
    val query: String?,
    val type: String? = null,
    val page: Int,
    val size: Int
)