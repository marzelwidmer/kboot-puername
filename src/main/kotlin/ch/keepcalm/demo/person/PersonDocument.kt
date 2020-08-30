package ch.keepcalm.demo.person

import ch.keepcalm.demo.person.model.FirstName
import ch.keepcalm.demo.person.model.Gender
import ch.keepcalm.demo.person.model.Person
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class PersonDocument(
        @Id
        val id: String = UUID.randomUUID().toString(),
        val firstName: String) {

        companion object{
                fun fromDomainObject(person: Person) = PersonDocument(firstName = person.firstName.value)
        }
        fun toDomainObject() = Person(firstName = FirstName(this.firstName), gender = Gender('M'))
}

