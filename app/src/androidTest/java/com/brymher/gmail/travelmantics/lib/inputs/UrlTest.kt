package com.brymher.gmail.travelmantics.lib.inputs

import org.junit.Assert.*
import org.junit.Test


class UrlTest {

    @Test
    fun validHttp() {
        assertTrue(Url("http://me.com").isValid)
    }

    @Test
    fun validHttps() {
        assertTrue(Url("https://me.com").isValid)
    }

    @Test
    fun invalidHttp() {
        assertTrue(Url("http://me?.com").isValid)
    }

    @Test
    fun invalidHttps() {
        assertTrue(Url("https://me?.com").isValid)
    }
}