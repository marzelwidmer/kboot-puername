package ch.keepcalm.demo

import ch.keepcalm.demo.person.PersonRouterConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
@Import(PersonRouterConfiguration::class)
class PersonRouterTestIT(@Autowired val client: WebTestClient) {

    @Test
    fun `Get all Persons`() {
        client.get()
            .uri("/api/persons")
            .exchange()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectStatus().isOk
    }
}
