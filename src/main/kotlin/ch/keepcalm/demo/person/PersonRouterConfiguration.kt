package ch.keepcalm.demo.person

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.router

@Configuration
class PersonRouterConfiguration {

    @Bean
    fun router() = router {
        "api".nest {
            GET("/persons") {
                ok().contentType(MediaType.APPLICATION_JSON).body(fromValue("bar"))
            }
        }
    }
}
