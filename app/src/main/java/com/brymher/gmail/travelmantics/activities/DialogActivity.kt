package com.brymher.gmail.travelmantics.activities

import android.app.AlertDialog

abstract class DialogActivity(layout: Int) : Base(layout) {
    fun showDialog(builder: AlertDialog.Builder.() -> Unit) {
        AlertDialog.Builder(this).apply(builder)
    }
}