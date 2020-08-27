package ch.keepcalm.demo.model

// Domain Primitive
data class FirstName private constructor(private val value: String) {
    companion object {
        // Create Object
        operator fun invoke(value: String) = FirstName(validate(value))
        private fun validate(value: String): String {
            check(value.isNotEmpty()) { "firstName must be non-empty" }
            check(value.trim().length >= 2) { "wrong firstName length" }
            return value
        }
    }
}
