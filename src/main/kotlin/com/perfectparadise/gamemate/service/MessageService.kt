package com.perfectparadise.gamemate.service

import com.corundumstudio.socketio.listener.DataListener
import com.perfectparadise.gamemate.model.authentication.PlatformAuthentication
import com.perfectparadise.gamemate.model.dto.MessageDto
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

const val EVENT_SEND_MESSAGE = "send_message"
const val EVENT_RECEIVE_MESSAGE = "receive_message"

@Service
class MessageService(
    val socketIOService: SocketIOService,
) {
    private val logger = KotlinLogging.logger { }

    @PostConstruct
    fun init() {
        socketIOService.socketIOServer.addEventListener(EVENT_SEND_MESSAGE, MessageDto::class.java, onChatReceived())
    }

    fun onChatReceived(): DataListener<MessageDto> {
        return DataListener<MessageDto> { client, data, _ ->
            val authentication: PlatformAuthentication = client.getAuthentication()

            logger.info { "Message received from ${authentication.platformUser}: $data" }

            socketIOService.broadcast(EVENT_RECEIVE_MESSAGE, data)
            // TODO: save the message to the database
        }
    }
}