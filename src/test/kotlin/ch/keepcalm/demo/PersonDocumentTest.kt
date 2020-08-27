package ch.keepcalm.demo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.GenericContainer
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

//    companion object {
//        val mongoDB = KMongoDBContainer("mongo:4.2.5").apply {
//            this.start()
//        }
//
//        @JvmStatic
//        @DynamicPropertySource
//        fun properties(registry: DynamicPropertyRegistry) {
//            registry.add("spring.data.mongodb.uri", mongoDB::uri);
//        }
//    }


    @Test
    fun persist() {
        StepVerifier.withVirtualTime {
            repository.save(PersonDocument(name = "foo"))
        }.expectNextMatches {
                it.name == "foo"
        }.verifyComplete()
    }
}


//class KMongoDBContainer(imageName: String) : GenericContainer<KMongoDBContainer>(imageName) {
//    val uri: String
//        get() {
//            val ip = this.containerIpAddress
//            val port = getMappedPort(PORT)
//            return String.format("mongodb://%s:%s/test", ip, port)
//        }
//
//    override fun stop() {
//        // let the JVM handle the shutdown
//    }
//
//    companion object {
//        private const val PORT = 27017
//    }
//
//    init {
//        addExposedPort(PORT)
//    }
//}







