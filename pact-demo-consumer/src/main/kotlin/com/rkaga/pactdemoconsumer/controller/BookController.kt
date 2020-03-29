package com.rkaga.pactdemoconsumer.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class BookController(
        private val restTemplate: RestTemplate
){
    final val API_URL = "http://localhost:8082"

    @GetMapping("/")
    fun getBooks() : String {
        return ObjectMapper().writeValueAsString(
                restTemplate.getForObject("$API_URL/books", Any::class.java).toString()
        )
    }

    @GetMapping("/{bookId}")
    fun getBook(
            @PathVariable(name = "bookId") bookId: Int
    ) : String {
        return ObjectMapper().writeValueAsString(
                restTemplate.getForObject("$API_URL/book/$bookId", Any::class.java).toString()
        )
    }
}
