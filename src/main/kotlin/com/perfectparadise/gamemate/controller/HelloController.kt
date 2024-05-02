package com.perfectparadise.gamemate.controller

import com.perfectparadise.gamemate.service.HelloService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(
    val helloService: HelloService
) {

    @GetMapping("/hello")
    fun hello(@RequestParam name: String): String {
        return helloService.helloToYou(name)
    }
}