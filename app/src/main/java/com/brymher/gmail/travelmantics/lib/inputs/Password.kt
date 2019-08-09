package com.brymher.gmail.travelmantics.lib.inputs

class Password(password: String, private val repeat: String, val minSize: Int = 5, val maxSize: Int = 10) :
    StringValidatable(password) {

    val tests = arrayOf(
        {
            value == repeat
        }
        ,
        {
            value.length > minSize
        },
        {
            value.length < maxSize
        }
    )

    private val ERRORS = arrayOf("'s do not match", " too short", " too long")

    override fun validate(): Boolean {
        var i = 0
        for (test in tests) {

            if (!test()) {
                error = "Password" + ERRORS[i]
                return false
            }

            i += 1
        }

        return true
    }

    class SignIn(password: String) : StringValidatable(password) {
        override fun validate(): Boolean {
            return value.isNotEmpty()
        }
    }

}