package ch.keepcalm.demo

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface PersonRepository : ReactiveMongoRepository<PersonDocument, String> {
    fun findByFirstName(firstName: String): Flux<PersonDocument>
}
