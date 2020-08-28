package ch.keepcalm.demo

import ch.keepcalm.demo.model.FirstName
import ch.keepcalm.demo.model.Gender
import ch.keepcalm.demo.model.Person
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*


@Repository
interface PersonRepository : ReactiveMongoRepository<PersonDocument, String>{
//        fun findByFirstName(firstName: String) : Mono<PersonDocument>
//        fun findByLastname(lastname: Mono<String?>?): Flux<Person?>?

        fun findByFirstName(firstName: String): Flux<PersonDocument?>?


}

@Document
data class PersonDocument(
        @Id
        val id: String = UUID.randomUUID().toString(),
        val firstName: String) {

        companion object{
                fun fromDomainObject(person: Person) = PersonDocument(firstName = person.firstName.toString())
        }
        fun toDomainObject() = Person(firstName = FirstName(this.firstName), gender = Gender('M'))
}

