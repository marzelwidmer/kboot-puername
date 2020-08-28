package ch.keepcalm.demo

import ch.keepcalm.demo.model.FirstName
import ch.keepcalm.demo.model.Gender
import ch.keepcalm.demo.model.Person
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import reactor.test.StepVerifier

@DataMongoTest
@Testcontainers
class PersonMongoDbTestIT @Autowired constructor(private val repository: PersonRepository) {

    companion object {
        @Container
        val container = MongoDBContainer("mongo:4.2.5").apply {
            this.start()
        }
        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
        }
    }


    @Test
    fun `Persist a Domain object as MongoDB object` () {
        val personDocument= PersonDocument(firstName = "John")
        val person = Person(firstName = FirstName("John"), gender = Gender('M'))

        StepVerifier.withVirtualTime {
            repository.save(PersonDocument(firstName = "John"))
        }.assertNext{
            it.firstName shouldBeEqualTo "John"
            it.id.shouldNotBeNull()
        }.verifyComplete()
    }

    @Test
    fun `Convert MongoDB Object to Domain object` () {
        StepVerifier.withVirtualTime {
            repository.findByFirstName("John")
        }.assertNext{
            it?.toDomainObject()?.firstName?.value shouldBeEqualTo "John"
        }.verifyComplete()
    }
}
