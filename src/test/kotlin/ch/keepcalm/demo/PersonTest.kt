package ch.keepcalm.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class PersonTest {

    @ParameterizedTest(name = "{index} negative test gender {0}")
    @ValueSource(chars = ['A','B'])
    fun `Person negative creation test - only gender M od F is allowed `(input: Char) {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(name = "Kim", gender = input)

        }
    }

    @ParameterizedTest(name = "{index} test gender {0}")
    @ValueSource(chars = ['F','M'])
    fun `Person creation test - it must have a valid gender F 👧 or M 🧔`(input: Char) {
        Person(name = "Kim", gender = input)
    }

    @ParameterizedTest(name = "{index} negative test gender {0}")
    @ValueSource(chars = ['f','m'])
    fun `Person creation test - gender have to be capital`(input: Char) {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(name = "Kim", gender = input)
        }
    }
}