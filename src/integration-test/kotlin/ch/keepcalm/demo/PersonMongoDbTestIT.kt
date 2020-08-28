package ch.keepcalm.demo

import ch.keepcalm.demo.model.FirstName
import ch.keepcalm.demo.model.Gender
import ch.keepcalm.demo.model.Person
import org.amshove.kluent.`should be equal to`
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
        val person = Person(firstName = FirstName("John"), gender = Gender('M'))

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
    fun `Test should store and retrieve` () {
        val personPublisher = repository.save(PersonDocument(firstName = "John"))
        StepVerifier.create(personPublisher).expectNextMatches {
            it.firstName == "John"
        }.verifyComplete()
    }


    @Test
    fun `Test Mapping Domain Object to Mongo Document Object` () {
        StepVerifier.withVirtualTime {
            repository.save(PersonDocument.fromDomainObject(person))
        }.assertNext{
            it.firstName shouldBeEqualTo "John"
            it.id.shouldNotBeNull()
        }.verifyComplete()
    }
    @Test
    fun `Test Mapping from Mongo Document Object to Domain Object` () {
        StepVerifier.withVirtualTime {
            repository.save(PersonDocument(firstName = "John"))
        }.assertNext{
            it?.toDomainObject()?.firstName?.value shouldBeEqualTo "John"
        }.verifyComplete()
    }

    @Test
    fun `Search Person byFirstName and convert it to Domain object` () {
        repository.save(PersonDocument(firstName = "Foo")).block()
        StepVerifier.withVirtualTime {
            repository.findByFirstName("Foo")
        }.assertNext{
            it?.toDomainObject()?.firstName?.value shouldBeEqualTo "Foo"
        }.verifyComplete()
    }

    @Test
    fun `Test Repository should save and findByFirstName` () {
        val sPerson = repository.save(PersonDocument(firstName = "Jack"))
        val foundPersons = sPerson.thenMany(repository.findByFirstName("Jack"))
        StepVerifier.create(foundPersons)
            .expectNextMatches {
                it.firstName == "Jack"
            }.verifyComplete()
    }
}
