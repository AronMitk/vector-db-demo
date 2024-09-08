package com.harrontech.vector_db_demo

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository: MongoRepository<Product, String> {
    fun findByDescriptionLike(description: String, pageable: Pageable): List<Product>
    fun findAllByIdIn(ids: List<String>, pageable: Pageable): List<Product>
}