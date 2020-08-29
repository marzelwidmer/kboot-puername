package ch.keepcalm.demo

import ch.keepcalm.demo.model.FirstName
import ch.keepcalm.demo.model.Gender
import ch.keepcalm.demo.model.Person
import io.github.serpro69.kfaker.Faker
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PersonTest {

    @ParameterizedTest(name = "{index} negative test gender {0}")
    @ValueSource(chars = ['a', 'A', 'b', 'B', 'c', 'C', 'd', 'D', 'e', 'E', '1', 'g', 'G', 'h', 'H', 'i', 'I', 'j', 'J', 'k', 'K', 'l', 'L', '2', 'n', 'N', 'o', 'O', 'p', 'P', 'q', 'Q', 'r', 'R',
        's', 'S', 't', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'])
    fun `Person negative creation test - only gender M od F is allowed `(input: Char) {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(firstName = FirstName(Faker().name.firstName()), gender = Gender(input))
        }
    }

    @ParameterizedTest(name = "{index} test gender {0}")
    @ValueSource(chars = ['f','F', 'm','M'])
    fun `Person creation test - it must have a valid gender fF 👧 or mM 🧔`(input: Char) {
        Person(firstName = FirstName(Faker().name.firstName()), gender = Gender(input))
    }


    @Test
    fun `test person firstName that is it not empty`() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(firstName = FirstName(""), gender = Gender(Faker().gender.unique.shortBinaryTypes().single()))
        }
    }

    @Test
    fun `test person firstName have the correct length`() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(firstName = FirstName("B"), gender = Gender(Faker().gender.unique.shortBinaryTypes().single()))
        }
    }
}
