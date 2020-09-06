package ch.keepcalm.demo.contract

import ch.keepcalm.demo.person.PersonDocument
import io.restassured.module.webtestclient.RestAssuredWebTestClient
import org.junit.jupiter.api.BeforeEach
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux

abstract class PersonRestBase {

    @BeforeEach
    fun setup() {
        RestAssuredWebTestClient
            .webTestClient(
                WebTestClient
                    .bindToRouterFunction(router {
                        "api".nest {
                            GET("/persons") {
                                ok().contentType(MediaType.APPLICATION_JSON)
                                    .body<Any>(Flux.just(PersonDocument(firstName = "John")))
                            }
                        }
                    }).build())
    }
}
