package com.harrontech.vector_db_demo

import com.harrontech.vector_db_demo.dto.MergeProductRequest
import org.springframework.transaction.annotation.Transactional

open class ProductService(val repository: ProductRepository) {

    @Transactional
    open fun createProduct(request: MergeProductRequest) {



    }
}