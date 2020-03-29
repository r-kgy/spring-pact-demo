package com.rkaga.pactdemoconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PactDemoConsumerApplication

fun main(args: Array<String>) {
	runApplication<PactDemoConsumerApplication>(*args)
}
