package ch.keepcalm.demo.person

import org.springframework.stereotype.Component

@Component
class PersonHandler(val repository: PersonRepository) {

    fun listPersons() = repository.findAll()

}


