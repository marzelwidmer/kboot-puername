package ch.keepcalm.demo.model

// Domain Primitive
data class Gender private constructor(val value: Char) {
    companion object {
        // Create Object
        operator fun invoke(value: Char) = Gender(validate(value = value))
        private fun validate(value: Char): Char {
            check(value == 'M' || value == 'F') { "gender ist invalid (M|F)" }
            return value
        }
    }
}
