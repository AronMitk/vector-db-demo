package com.harrontech.vector_db_demo.v3

import org.springframework.ai.transformers.TransformersEmbeddingClient
import org.springframework.stereotype.Service

@Service
class EmbeddingService(val embeddingClient: TransformersEmbeddingClient) {
    fun createEmbedding(text: String): List<Float> {
        embeddingClient.afterPropertiesSet()
        val embeddings = embeddingClient.embed(text)
        return embeddings.map { it.toFloat() }
    }
}