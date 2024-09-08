package com.harrontech.vector_db_demo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document
class Product(
    @Id
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var description: String
)