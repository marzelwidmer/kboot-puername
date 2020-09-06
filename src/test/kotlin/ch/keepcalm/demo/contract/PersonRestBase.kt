package ch.keepcalm.demo.contract

import ch.keepcalm.demo.person.PersonDocument
import io.restassured.module.webtestclient.RestAssuredWebTestClient
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInfo
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
abstract class PersonRestBase {

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider?, testInfo: TestInfo) {
        RestAssuredWebTestClient
            .webTestClient(webTestClient(restDocumentation))
    }


    private fun webTestClient(restDocumentation: RestDocumentationContextProvider?) = WebTestClient
        .bindToRouterFunction(apiPersons())
        .configureClient()
        .filter(documentationConfiguration(restDocumentation))
        .build()

    private fun webTestClient2(restDocumentation: RestDocumentationContextProvider?) = WebTestClient
        .bindToRouterFunction(apiPersons())
        .configureClient().baseUrl("https://api.example.com")
        .filter(documentationConfiguration(restDocumentation))
        .build()

    private fun webTestClient3(restDocumentation: RestDocumentationContextProvider?) = WebTestClient
        .bindToRouterFunction(apiPersons())
        .configureClient()
        .baseUrl("https://api.example.com")
        .filter(documentationConfiguration(restDocumentation)
            .operationPreprocessors()
            .withRequestDefaults(Preprocessors.prettyPrint())
            .withResponseDefaults(Preprocessors.prettyPrint()))
        .build()

    private fun apiPersons(): RouterFunction<ServerResponse> = router {
        "api".nest {
            GET("/persons") {
                ok().contentType(MediaType.APPLICATION_JSON)
                    .body<Any>(Flux.just(PersonDocument(firstName = "John")))
            }
        }
    }

}
