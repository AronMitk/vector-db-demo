package com.harrontech.vector_db_demo.v3

import io.qdrant.client.QdrantClient
import io.qdrant.client.QdrantGrpcClient
import org.springframework.ai.embedding.EmbeddingClient
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class VectorConfig {

    @Bean
    fun client(): QdrantClient = QdrantClient(
        QdrantGrpcClient.newBuilder("localhost", 6334, false).build()
    )
}