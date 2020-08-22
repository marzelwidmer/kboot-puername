package ch.keepcalm.demo

// Domain Primitive
class Person private constructor(val name: String, val gender: Char) {

    companion object {
        // Create Object
        operator fun invoke(name: String, gender: Char) = Person(validateName(name = name), validateGender(gender = gender))

        private fun validateName(name: String): String {
            check(name.isNotEmpty()) { "name must be non-empty" }
            check(name.trim().length >= 2) { "wrong name length" }
            return name
        }
        private fun validateGender(gender: Char): Char {
            check(gender.equals('M') || gender.equals('F') ) { "gender ist invalid (M|F)" }
            return gender
        }
    }
}
