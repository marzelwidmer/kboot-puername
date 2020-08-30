package ch.keepcalm.demo

import ch.keepcalm.demo.person.handler.PersonHandler
import ch.keepcalm.demo.person.repository.PersonDocument
import ch.keepcalm.demo.person.service.PersonService
import ch.keepcalm.demo.person.routes.PersonRouter
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux


import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@WebFluxTest
@Import(value = [PersonRouter::class])
class PersonRouterTestIT(@Autowired private val webTestClient: WebTestClient) {

    @MockBean
    private lateinit var handler: PersonHandler
    @MockBean
    private lateinit var service: PersonService

    @Test
    fun `Give back Persons from the Web Tier with Mockito`() {
        `when`(this.handler.findAll())
            .thenReturn(Flux.just(PersonDocument(firstName = "John")))

        webTestClient.get()
            .uri("/api/persons")
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk
            .expectBody().jsonPath("@.[0].firstName").isEqualTo("John")
    }

    @Test
    fun `Test All Persons - Web Tier`() {
        `when`(this.service.findAll())
            .thenReturn(Flux.just(PersonDocument(firstName = "John")))

        webTestClient.get()
            .uri("/persons")
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk
            .expectBody().jsonPath("@.[0].firstName").isEqualTo("John")
    }
}
