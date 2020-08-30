package ch.keepcalm.demo.person

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
                GET("/persons/search") {
                    val name = it.queryParam("name").get()
                    ok().contentType(MediaType.APPLICATION_JSON).body<Any>(handler.findByFirstName(firstName = name))
                }
            }
        }
    }
}

