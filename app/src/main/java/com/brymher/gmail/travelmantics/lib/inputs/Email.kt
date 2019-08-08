package com.brymher.gmail.travelmantics.lib.inputs

class Email(email: String) : Validatable<String>(email) {
    override fun validate(): Boolean {
        val chars = "a-zA-Z0-9~`!#$%^&*(){}:\"';,<.>?/"
        return "[$chars]+@[$chars]+\\.[a-zA-Z]{0,3}".toRegex() matches value
    }

}