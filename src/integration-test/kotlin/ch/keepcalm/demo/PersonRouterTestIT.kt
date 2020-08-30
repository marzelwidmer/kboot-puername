package ch.keepcalm.demo

import ch.keepcalm.demo.person.handler.PersonHandler
import ch.keepcalm.demo.person.repository.PersonDocument
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

@WebFluxTest
@Import(value = [PersonRouter::class])
class PersonRouterTestIT(@Autowired private val webTestClient: WebTestClient) {

    @MockBean
    private lateinit var handler: PersonHandler

    @Test
    fun `Give back Persons from the Web Tier with Mockito`() {
        // Given
        val john = Flux.just(PersonDocument(firstName = "John"))
        // When
        `when`(this.handler.listPersons())
            .thenReturn(john)
        // Then
        webTestClient.get()
            .uri("/api/persons")
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk
            .expectBody().jsonPath("@.[0].firstName").isEqualTo("John")
    }
}
