package ch.keepcalm.demo.person.model

// Domain Primitive
data class Gender private constructor(val value: Char) {
    companion object {
        // Create Object
        operator fun invoke(value: Char) = Gender(validate(value = value.toLowerCase()))
        private fun validate(value: Char): Char {
            check(value == 'm' || value == 'f') { "gender ist invalid (m|f)" }
            return value
        }
    }
}
