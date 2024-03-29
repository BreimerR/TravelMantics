package com.brymher.gmail.travelmantics.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity

abstract class Base(private val LAYOUT: Int) : AppCompatActivity() {

    open val menu: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)

        init(savedInstanceState)
    }

    protected abstract fun init(savedInstanceState: Bundle?)

    protected fun startActivity(clazz: Class<CreateAccount>) {
        startActivity(Intent(baseContext, clazz))
    }

    fun inflateMenu(menu: Menu?): Boolean {
        return if (this.menu != null) {
            menuInflater.inflate(this.menu!!, menu)
            true
        } else false
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if (inflateMenu(menu)) {
            true
        } else super.onCreateOptionsMenu(menu)
    }


    fun onCreateOptionsMenu(menu: Menu?, action: () -> Unit): Boolean {
        return if (inflateMenu(menu)) {
            action()
            true
        } else super.onCreateOptionsMenu(menu)
    }

    fun <C : AppCompatActivity> startActivity(context: C, clazz: Class<out AppCompatActivity>) {
        startActivity(Intent(context, clazz))
    }

    fun startActivity(context: AppCompatActivity, clazz: Class<out AppCompatActivity>, builder: Intent.() -> Unit) {
        startActivity(Intent(context, clazz).apply(builder))
    }

}