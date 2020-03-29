package com.rkaga.pactdemoprovider.pact

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.TestTemplate
import au.com.dius.pact.provider.junit5.HttpTestTarget
import org.junit.jupiter.api.BeforeEach
import au.com.dius.pact.provider.junit.loader.PactFolder
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@Provider("BookProvider")
@PactFolder("../pact-demo-consumer/build/pacts")
class BookProviderTest {

    @BeforeEach
    internal fun setupTestTarget(context: PactVerificationContext) {
        context.target = HttpTestTarget("localhost", 8082, "/")
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    internal fun pactVerificationTestTemplate(context: PactVerificationContext) {
        context.verifyInteraction()
    }

    @State("Books exist")
    fun booksExist() {}

    @State("Book exist")
    fun bookExist() {}
}
