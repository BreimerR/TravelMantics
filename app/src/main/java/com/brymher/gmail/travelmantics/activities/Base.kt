package com.brymher.gmail.travelmantics.activities

import android.os.Bundle
import android.view.Menu
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

abstract class Base(private val LAYOUT: Int) : AppCompatActivity() {

    open val menu: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        init(savedInstanceState)
    }

    protected abstract fun init(savedInstanceState: Bundle?)

    fun startActivity(clazz: Class<CreateAccount>) {
        startActivity(Intent(baseContext, clazz))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if (this.menu != null) {
            menuInflater.inflate(this.menu as Int, menu)
            return true
        } else super.onCreateOptionsMenu(menu)
    }
}