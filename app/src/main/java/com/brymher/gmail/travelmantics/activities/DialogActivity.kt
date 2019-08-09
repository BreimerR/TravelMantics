package com.brymher.gmail.travelmantics.activities

import android.app.AlertDialog
import kotlinx.android.synthetic.main.sign_in.*

abstract class DialogActivity(layout: Int) : Base(layout) {
    fun showDialog(builder: AlertDialog.Builder.() -> Unit) {
        showDialog(AlertDialog.Builder(this), builder)
    }

    fun showDialog(layout: Int, theme: Int = R.style.AppTheme_AlertDialog): AlertDialog? {
        return AlertDialog.Builder(this, theme).apply {
            setView(layout)
        }.show()
    }

    fun showDialog(
        layout: Int,
        theme: Int = R.style.AppTheme_AlertDialog,
        onShow: (AlertDialog) -> Unit
    ) {
        onShow(AlertDialog.Builder(this, theme).apply {
            setView(layout)
        }.show())
    }


    fun showDialog(dialog: AlertDialog.Builder, builder: AlertDialog.Builder.() -> Unit) {
        dialog.apply(builder)
    }
}