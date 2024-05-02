package com.perfectparadise.gamemate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GameMateBackendApplication

fun main(args: Array<String>) {
    runApplication<GameMateBackendApplication>(*args)
}
