package ch.keepcalm.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class PersonTest {

    @Test
    fun `Person creation test - it must have a valid gender M 🧔`() {
        Person(name = "Basco", gender = 'M')
    }

    @Test
    fun `Person creation test - gender X is not allowed `() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(name = "Diana", gender = 'X')

        }
    }

    @Test
    fun `Person creation test - it must have a valid gender F 👧`() {
        Person(name = "Diana", gender = 'F')
    }

    @Test
    fun `Person creation test - gender have to be capital`() {
        Assertions.assertThrows(IllegalStateException::class.java) {
            Person(name = "Diana", gender = 'f')

        }
    }
}
