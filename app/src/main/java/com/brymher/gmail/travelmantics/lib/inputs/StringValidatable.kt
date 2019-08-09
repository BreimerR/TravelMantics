package com.brymher.gmail.travelmantics.lib.inputs

abstract class StringValidatable(value: String) : Validatable<String>(value) {
    override fun toString(): String {
        return value
    }
}