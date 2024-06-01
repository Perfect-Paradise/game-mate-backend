package com.perfectparadise.gamemate.config

import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.protocol.JacksonJsonSupport
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SocketIOConfig(
    @Value("\${socket-io.server.host}")
    val host: String,
    @Value("\${socket-io.server.port}")
    val port: Int,
) {

    @Bean
    fun socketIOServer(): SocketIOServer {
        val config = com.corundumstudio.socketio.Configuration()
        config.hostname = host
        config.port = port
        config.jsonSupport = CustomJacksonJsonSupport()

        return SocketIOServer(config)
    }
}

class CustomJacksonJsonSupport : JacksonJsonSupport() {
    override fun init(objectMapper: ObjectMapper) {
        super.init(objectMapper)
        objectMapper.registerModule(kotlinModule())
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
}
