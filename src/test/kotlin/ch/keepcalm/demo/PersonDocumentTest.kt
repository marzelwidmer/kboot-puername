package ch.keepcalm.demo

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
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
class PersonDocumentTest @Autowired constructor(private val repository: PersonRepository) {

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
    fun persist() {
        StepVerifier.withVirtualTime {
            repository.save(PersonDocument(name = "Bill"))
        }.assertNext{
            assertEquals("Bill", it.name)
            assertNotNull(it.id)
        }.verifyComplete()
    }

}
