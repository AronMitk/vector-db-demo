package com.harrontech.vector_db_demo

import com.harrontech.vector_db_demo.dto.GetProductResponse
import com.harrontech.vector_db_demo.dto.QueryRequest
import com.harrontech.vector_db_demo.dto.MergeProductRequest
import com.harrontech.vector_db_demo.dto.GetPagedProductResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

interface ProductsController {
    @GetMapping("{id}")
    fun findById(@PathVariable id: String): GetProductResponse?

    @GetMapping
    fun getByQuery(@RequestBody query: QueryRequest): GetPagedProductResponse

    @PostMapping
    fun create(@RequestBody request: MergeProductRequest): GetProductResponse

    @PutMapping("{id}")
    fun update(@PathVariable id: String, @RequestBody request: MergeProductRequest): GetProductResponse

    @DeleteMapping("{id}")
    fun deleteById(@PathVariable id: String)

    @DeleteMapping
    fun deleteAll()

    fun getVersion(): ControllerVersion
}