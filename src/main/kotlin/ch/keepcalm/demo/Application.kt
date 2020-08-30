package ch.keepcalm.demo

import ch.keepcalm.demo.person.repository.PersonDocument
import ch.keepcalm.demo.person.repository.PersonRepository
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import reactor.kotlin.core.publisher.toFlux

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        addInitializers(
            beans {
                profile("clean"){
                    bean {
                        ApplicationRunner {
                            val repository = ref<PersonRepository>()
                            repository.deleteAll()
                                .subscribe { println(it) }
                        }
                    }
                }
                profile("local"){
                    bean {
                        ApplicationRunner {
                            val repository = ref<PersonRepository>()

                            repository.deleteAll().thenMany(
                                listOf("Margherita", "Fungi", "Proscuttio", "Napoli", "Quattro Formaggio", "Calzone", "Rustica")
                                    .toFlux()
                                    .map { PersonDocument(firstName = it) })
                                .flatMap { repository.save(it) }
                                .thenMany(repository.findAll())
                                .subscribe { println(it) }
                        }
                    }
                }
            }
        )
    }
}

