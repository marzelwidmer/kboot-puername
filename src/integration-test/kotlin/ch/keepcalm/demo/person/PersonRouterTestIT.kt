package ch.keepcalm.demo.person

import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux

@WebFluxTest
@Import(value = [PersonRouter::class])
class PersonRouterTestIT(@Autowired private val webTestClient: WebTestClient) {

    @MockBean
    private lateinit var handler: PersonHandler

    @Test
    fun `Give back Persons from the Web Tier with Mockito`() {
        // When
        `when`(this.handler.listPersons())
            .thenReturn(Flux.just(PersonDocument(firstName = "John")))
        // Then
        webTestClient.get()
            .uri("/api/persons")
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk
            .expectBody().jsonPath("@.[0].firstName").isEqualTo("John")
    }

    @Test
    fun `Search Persons byName`() {
        // When
        `when`(this.handler.findByFirstName("John"))
            .thenReturn(Flux.just(PersonDocument(firstName = "John")))
        // Then
        webTestClient.get()
            .uri("/api/persons/search?name=John")
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk
            .expectBody().jsonPath("@.[0].firstName").isEqualTo("John")
    }
}
