package com.brymher.gmail.travelmantics.lib.inputs


abstract class Validatable<T>(val value: T) {

    var error: String? = null

    private var VALID: Boolean? = null
        get() {

            if (field == null)
                field = validate()

            return field
        }


    val isValid: Boolean
        get() = VALID as Boolean


    abstract fun validate(): Boolean
}


