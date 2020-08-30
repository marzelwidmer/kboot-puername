package ch.keepcalm.demo.person.service

import ch.keepcalm.demo.person.repository.PersonRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
class PersonService(val repository: PersonRepository) {

    fun findAll() = repository.findAll()
}
