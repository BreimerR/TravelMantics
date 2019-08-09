package com.brymher.gmail.travelmantics.lib.inputs

class Email(email: String) : StringValidatable(email) {
    override fun validate(): Boolean {
        val chars = "a-zA-Z0-9~`!#$%^&*(){}:\"';,<.>?/"
        return value.isNotEmpty() && "[$chars]+@[$chars]+\\.[a-zA-Z]{0,3}".toRegex() matches value
    }

}