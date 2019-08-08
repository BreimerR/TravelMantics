package com.brymher.gmail.travelmantics.lib.inputs

class Name(name: String) : Validatable<String>(name) {
    override fun validate(): Boolean {
        return value.isNotEmpty()
    }

}