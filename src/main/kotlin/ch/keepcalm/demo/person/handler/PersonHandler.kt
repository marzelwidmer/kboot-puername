package ch.keepcalm.demo.person.handler

import ch.keepcalm.demo.person.repository.PersonRepository
import org.springframework.stereotype.Component

@Component
class PersonHandler(val repository: PersonRepository) {

    fun findAll() = repository.findAll()
}
