package com.harrontech.vector_db_demo.v2

import com.harrontech.vector_db_demo.ControllerVersion
import com.harrontech.vector_db_demo.ProductsController
import com.harrontech.vector_db_demo.dto.GetPagedProductResponse
import com.harrontech.vector_db_demo.dto.GetProductResponse
import com.harrontech.vector_db_demo.dto.MergeProductRequest
import com.harrontech.vector_db_demo.dto.QueryRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v2")
class ProductsControllerV2(val service: ProductsServiceV2): ProductsController {
    override fun findById(id: String): GetProductResponse? {
        val product = service.findById(id)
        return GetProductResponse.mapFrom(product)
    }

    override fun getByQuery(query: QueryRequest): GetPagedProductResponse {
        val product = service.getByQueryWithPoints(query)
        return GetPagedProductResponse.mapFromWithPoints(product, query.page, query.size)
    }

    override fun create(request: MergeProductRequest): GetProductResponse {
        val product = service.create(request)
        return GetProductResponse.mapFrom(product)
    }

    override fun update(id: String, request: MergeProductRequest): GetProductResponse {
        val product = service.update(id, request)
        return GetProductResponse.mapFrom(product)
    }

    override fun deleteById(id: String) {
        service.deleteById(id)
    }

    override fun deleteAll() {
        service.deleteAll()
    }

    override fun getVersion(): ControllerVersion {
        return ControllerVersion.V2
    }
}