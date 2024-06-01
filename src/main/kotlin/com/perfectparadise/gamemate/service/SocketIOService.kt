package com.perfectparadise.gamemate.service

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.perfectparadise.gamemate.model.authentication.PlatformAuthentication
import com.perfectparadise.gamemate.model.authentication.anonymousAuthentication
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.SmartLifecycle
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicBoolean

const val CLIENT_STORE_KEY_AUTHENTICATION = "platformAuthentication"

fun SocketIOClient.setAuthentication(authentication: PlatformAuthentication) {
    this.set(CLIENT_STORE_KEY_AUTHENTICATION, authentication)
}

fun SocketIOClient.getAuthentication(): PlatformAuthentication {
    return this.get(CLIENT_STORE_KEY_AUTHENTICATION)
}

@Service
class SocketIOService(
    val socketIOServer: SocketIOServer,
    val jwtService: JwtService,
) : SmartLifecycle {

    private final val logger = KotlinLogging.logger { }

    val isRunning: AtomicBoolean = AtomicBoolean(false)

    override fun start() {
        isRunning.set(true)

        socketIOServer.addConnectListener(onConnect())
        socketIOServer.addDisconnectListener(onDisconnect())

        socketIOServer.start()
        logger.info { "Socket IO Server started" }
    }

    private fun onConnect(): ConnectListener {
        return ConnectListener { client ->
            logger.info { "Client connected: ${client.remoteAddress}" }
            if (!authenticate(client)) {
                client.disconnect()
                return@ConnectListener
            }
        }
    }

    private fun authenticate(client: SocketIOClient): Boolean {
        val token = client.handshakeData.getSingleUrlParam("token")
        if (token == null) {
            val platformAuthentication = anonymousAuthentication()
            logger.info { "Anonymous client" }
            client.setAuthentication(platformAuthentication)
            return true
        }

        try {
            val platformAuthentication = jwtService.parseToken(token)
            logger.info { "Client authenticated: ${platformAuthentication.platformUser.id}" }
            client.setAuthentication(platformAuthentication)
            return true
        } catch (e: Exception) {
            logger.error { "Invalid token" }
            return false
        }
    }

    private fun onDisconnect(): DisconnectListener {
        return DisconnectListener {
            logger.info { "Client disconnected: ${it.remoteAddress}" }
        }
    }

    override fun stop() {
        socketIOServer.stop()
        isRunning.set(false)
    }

    override fun isRunning(): Boolean {
        return isRunning.get()
    }

    fun broadcast(event: String, data: Any) {
        socketIOServer.broadcastOperations.sendEvent(event, data)
    }

}