package ch.keepcalm.demo

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Document
data class PersonDocument(
        @Id
        val id: ObjectId = ObjectId.get(),
        val name: String) {

}


@Repository
interface PersonRepository : ReactiveMongoRepository<PersonDocument, String>
