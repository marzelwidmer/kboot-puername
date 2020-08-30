package ch.keepcalm.demo.person.routes

import ch.keepcalm.demo.person.handler.PersonHandler
import ch.keepcalm.demo.person.service.PersonService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class PersonRouter {

    @Bean
    fun apiRouter(handler: PersonHandler, service: PersonService) = router {
        "api".nest {
            GET("/persons") {
                accept(MediaType.APPLICATION_JSON)
                ok().contentType(MediaType.APPLICATION_JSON).body<Any>(handler.findAll())
            }
        }

        GET("/persons") {
            accept(MediaType.APPLICATION_JSON)
            ok().contentType(MediaType.APPLICATION_JSON).body<Any>(service.findAll())
        }
    }
}

