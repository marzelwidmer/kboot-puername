package ch.keepcalm.demo.person

import ch.keepcalm.demo.domain.FirstName
import ch.keepcalm.demo.domain.Gender
import ch.keepcalm.demo.domain.Person
import io.github.serpro69.kfaker.Faker
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
class PersonMongoDbTestIT (@Autowired private val repository: PersonRepository) {

    companion object {
        val person = Person(firstName = FirstName(Faker().name.firstName()), gender = Gender(Faker().gender.unique.shortBinaryTypes().single()))
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
        val fakeName = Faker().name.firstName()
        val personPublisher = repository.save(PersonDocument(firstName = fakeName))
        StepVerifier.create(personPublisher).expectNextMatches {
            it.firstName == fakeName
        }.verifyComplete()
    }


    @Test
    fun `Test Mapping Domain Object to Mongo Document Object` () {
        StepVerifier.withVirtualTime {
            repository.save(PersonDocument.fromDomainObject(person))
        }.assertNext{
            it.firstName shouldBeEqualTo person.firstName.value
            it.id.shouldNotBeNull()
        }.verifyComplete()
    }
    @Test
    fun `Test Mapping from Mongo Document Object to Domain Object` () {
        val fakeName = Faker().name.firstName()
        StepVerifier.withVirtualTime {
            repository.save(PersonDocument(firstName = fakeName))
        }.assertNext{
            it?.toDomainObject()?.firstName?.value shouldBeEqualTo fakeName
        }.verifyComplete()
    }

    @Test
    fun `Search Person byFirstName and convert it to Domain object` () {
        val fakeName = Faker().name.firstName()

        repository.save(PersonDocument(firstName = fakeName)).block()
        StepVerifier.withVirtualTime {
            repository.findByFirstName(fakeName)
        }.assertNext{
            it?.toDomainObject()?.firstName?.value shouldBeEqualTo fakeName
        }.verifyComplete()
    }

    @Test
    fun `Test Repository should save and findByFirstName` () {
        val fakeName = Faker().name.firstName()
        val sPerson = repository.save(PersonDocument(firstName = fakeName))
        val foundPersons = sPerson.thenMany(repository.findByFirstName(fakeName))
        StepVerifier.create(foundPersons)
            .expectNextMatches {
                it.firstName == fakeName
            }.verifyComplete()
    }
}
