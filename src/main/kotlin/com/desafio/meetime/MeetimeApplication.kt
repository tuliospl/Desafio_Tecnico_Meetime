package com.desafio.meetime

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class MeetimeApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<MeetimeApplication>(*args)
}
