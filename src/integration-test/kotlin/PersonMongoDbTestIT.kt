package ch.keepcalm.demo.`integration-test`

import ch.keepcalm.demo.PersonDocument
import ch.keepcalm.demo.PersonRepository
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
    fun `Persist PersonDocument object`() {
        StepVerifier.withVirtualTime {
            repository.save(PersonDocument(firstName = "Bill"))
        }.assertNext{
            "Bills" shouldBeEqualTo it.firstName
            it.id.shouldNotBeNull()
        }.verifyComplete()
    }

    @Test
    fun `Persist a Domain Object as PersonDocument Object in MongoDB` () {
        val kim = Person(firstName = FirstName("Kim"), gender = Gender('M'))
        StepVerifier.withVirtualTime {
            repository.save(PersonDocument.fromDomainObject(kim))
        }.assertNext{
            it.firstName shouldBeEqualTo kim.firstName.toString()
        }.verifyComplete()
    }

    @Test
    fun `count records` () {
        val block = repository.count().block()
        println(block)

    }
}
