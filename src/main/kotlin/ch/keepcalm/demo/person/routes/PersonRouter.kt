package ch.keepcalm.demo.person.routes

import ch.keepcalm.demo.person.handler.PersonHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class PersonRouter {
    @Bean
    fun apiRouter(handler: PersonHandler) = router {
        accept(MediaType.APPLICATION_JSON).nest {
            "api".nest {
                GET("/persons") {
                    ok().contentType(MediaType.APPLICATION_JSON).body<Any>(handler.listPersons())
                }
            }
        }
    }
}

