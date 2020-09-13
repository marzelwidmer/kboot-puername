package ch.keepcalm.demo.contract

import ch.keepcalm.demo.person.PersonDocument
import io.restassured.module.webtestclient.RestAssuredWebTestClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux

@AutoConfigureRestDocs(outputDir = "target/snippets")
@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
abstract class PersonRestBase {

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider?) {
        RestAssuredWebTestClient.webTestClient(WebTestClient.bindToRouterFunction(
            apiPersons()
        ).configureClient()
            .filter(documentationConfiguration(restDocumentation))
            .build())
    }

    private fun apiPersons(): RouterFunction<ServerResponse> = router {
        "api".nest {
            GET("/persons") {
                ok().contentType(MediaType.APPLICATION_JSON)
                    .body<Any>(Flux.just(PersonDocument(firstName = "John")))
            }
        }
    }
}
