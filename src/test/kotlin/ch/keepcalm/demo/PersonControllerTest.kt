package ch.keepcalm.demo

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient


@ExtendWith(SpringExtension::class)
@WebFluxTest
@AutoConfigureWebTestClient
class PersonControllerTest (@Autowired val client: WebTestClient){

    @Test
    fun getAllPersons() {
        client
            .get()
            .uri("/persons")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("@.[0].firstName").isEqualTo("John")

    }
}

