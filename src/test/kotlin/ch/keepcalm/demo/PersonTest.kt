package ch.keepcalm.demo

import ch.keepcalm.demo.model.FirstName
import ch.keepcalm.demo.model.Gender
import ch.keepcalm.demo.model.Person
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class PersonTest {

    @ParameterizedTest(name = "{index} negative test gender {0}")
    @ValueSource(chars = ['A', 'B', 'C', 'D', 'E', '1', 'G', 'H', 'I', 'J', 'K', 'L', '2', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'])
    fun `Person negative creation test - only gender M od F is allowed `(input: Char) {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(firstName = FirstName("Kim"), gender = Gender(input))
        }
    }

    @ParameterizedTest(name = "{index} test gender {0}")
    @ValueSource(chars = ['F', 'M'])
    fun `Person creation test - it must have a valid gender F 👧 or M 🧔`(input: Char) {
        Person(firstName = FirstName("Kim"), gender = Gender(input))
    }

    @ParameterizedTest(name = "{index} negative test gender {0} have to be capital")
    @ValueSource(chars = ['f', 'm'])
    fun `Person creation test - gender have to be capital`(input: Char) {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(firstName = FirstName("Kim"), gender = Gender(input))
        }
    }

    @Test
    fun `test person firstName that is it not empty`() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(firstName = FirstName(""), gender = Gender('M'))
        }
    }

    @Test
    fun `test person firstName have the correct length`() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(firstName = FirstName("B"), gender = Gender('M'))
        }
    }

    @Test
    fun `test person firstName value as String`() {
        val john = Person(firstName = FirstName("John"), gender = Gender('M'))
        john.firstName.value`should be equal to` "John"
    }

}
