package com.woonit.wonnit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WonnitApplication

fun main(args: Array<String>) {
    runApplication<WonnitApplication>(*args)
}
