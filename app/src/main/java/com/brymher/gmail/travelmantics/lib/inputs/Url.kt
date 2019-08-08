package com.brymher.gmail.travelmantics.lib.inputs


class Url(url: String) : Validatable<String>(url) {
    override fun validate(): Boolean {
        val chars = "a-zA-Z0-9~`!#$%^&*(){}:\"';,<>?/"
        return "(http(s)?://)?[$chars](\\.[$chars])+".toRegex() matches value
    }

}