package com.brymher.gmail.travelmantics.models

open class Model {

    var errors = arrayOf<String>()

    val hasErrors: Boolean get() = errors.isNotEmpty()

}