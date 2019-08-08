package com.brymher.gmail.travelmantics.lib.inputs

import org.junit.Test

import org.junit.Assert.*

class EmailTest {

    @Test
    fun isValid() {
        assertTrue(Email("brymher@gmail.com").isValid)
    }
}