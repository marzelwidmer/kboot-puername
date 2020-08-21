package ch.keepcalm.demo

import org.amshove.kluent.*
import org.junit.jupiter.api.Test

class PersonTest {

    private val malePerson = Person(name = "Basco", gender = 'M')

    @Test
    fun `person is not null`(){
        malePerson.`should not be null`()
    }

    @Test
    fun `name of a person is not empty`(){
        malePerson.name.`should not be null or empty`()
    }

    @Test
    fun `male person must have a gender M`(){
        malePerson.gender.`should not be digit`()
        malePerson.gender.shouldBe('M')
    }
    @Test
    fun `male person do not have a gender F`(){
        malePerson.gender.`should not be digit`()
        malePerson.gender.shouldNotBe('F')
    }
}
