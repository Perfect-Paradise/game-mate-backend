package com.perfectparadise.gamemate.service

import org.springframework.stereotype.Service

@Service
class HelloService {

    fun helloToYou(yourName: String): String {
        return "Hello, $yourName!"
    }
}