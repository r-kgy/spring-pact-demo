package com.rkaga.pactdemoconsumer.pact

import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import org.junit.jupiter.api.extension.ExtendWith
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import org.junit.jupiter.api.Test
import au.com.dius.pact.consumer.MockServer
import java.util.HashMap
import org.apache.commons.collections4.MapUtils
import org.apache.http.client.fluent.Request;
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.`is`
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import java.io.BufferedReader


@ExtendWith(PactConsumerTestExt::class)
@PactTestFor(providerName = "BookProvider")
class PactBookConsumerTest {
    private val headers = MapUtils.putAll(HashMap<String, String>(), arrayOf("Content-Type", "application/json"))

    @Pact(consumer = "BookConsumer")
    fun books(builder: PactDslWithProvider): RequestResponsePact {
        return builder
                .given("Books exist")
                .uponReceiving("all books data")
                .path("/books")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body("[{\"id\":1,\"name\":\"sample book a\",\"price\":{\"taxExclude\":1000,\"taxInclude\":1100}},{\"id\":2,\"name\":\"sample book b\",\"price\":{\"taxExclude\":500,\"taxInclude\":550}}]")
                .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "books")
    internal fun testBooks(mockServer: MockServer) {
        val httpResponse = Request.Get(mockServer.getUrl() + "/books").execute().returnResponse()
        assertThat(httpResponse.statusLine.statusCode, `is`(equalTo(200)))
        assertThat(httpResponse.entity.content.bufferedReader().use(BufferedReader::readText),
                `is`(equalTo(
                        "[{\"id\":1,\"name\":\"sample book a\",\"price\":{\"taxExclude\":1000,\"taxInclude\":1100}},{\"id\":2,\"name\":\"sample book b\",\"price\":{\"taxExclude\":500,\"taxInclude\":550}}]"
                )))
    }

    @Pact(consumer = "BookConsumer")
    fun book(builder: PactDslWithProvider): RequestResponsePact {
        return builder
                .given("Book exist")
                .uponReceiving("book data")
                .path("/book/1")
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(
                        PactDslJsonBody()
                                .integerType("id", 1)
                                .stringType("name", "sample book a")
                                .`object`("price")
                                .integerType("taxExclude", 1000)
                                .integerType("taxInclude", 1100)
                                .closeObject()
                                .close()
                )
                .toPact()
    }

    @Test
    @PactTestFor(pactMethod = "book")
    internal fun testBook(mockServer: MockServer) {
        val httpResponse = Request.Get(mockServer.getUrl() + "/book/1").execute().returnResponse()
        assertThat(httpResponse.statusLine.statusCode, `is`(equalTo(200)))
        assertThat(httpResponse.entity.content.bufferedReader().use(BufferedReader::readText),
                `is`(equalTo(
                        "{\"price\":{\"taxExclude\":1000,\"taxInclude\":1100},\"name\":\"sample book a\",\"id\":1}"
                )))
    }
}
