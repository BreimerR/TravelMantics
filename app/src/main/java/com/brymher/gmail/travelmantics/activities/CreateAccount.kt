package com.brymher.gmail.travelmantics.activities

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.brymher.gmail.travelmantics.lib.inputs.Email
import com.brymher.gmail.travelmantics.lib.inputs.Name
import com.brymher.gmail.travelmantics.lib.inputs.Password
import com.brymher.gmail.travelmantics.models.User
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccount : DialogActivity(R.layout.activity_create_account) {

    // models interact with base data classes
    var user: User? = null

    override fun init(savedInstanceState: Bundle?) {
        signUp?.setOnClickListener {

            user = User()

            user?.apply {
                val email = Email(email?.text.toString())
                val pwd = Password(password?.text.toString(), passwordR?.text.toString())

                val n = fName.text.toString()

                val name = if (n.isEmpty()) {
                    n
                } else {
                    val e = email.value

                    e.substring(0 until e.indexOf('@'))
                }

                register(email, pwd, Name(name), onRegisterSuccess, onRegisterFail)
            }


        }

        signIn?.setOnClickListener {
            startActivity(Intent(this, Welcome::class.java))
        }
    }


    private val onRegisterSuccess = {
        val d = AlertDialog.Builder(this@CreateAccount).apply {
            setTitle("Logged In")
            // show sign in dialog

        }.show()
    }

    val onRegisterFail = { it: Int ->
        when (it) {
            User.Validation.AUTH_CANCELED -> {
                Toast.makeText(this, "Upload Canceled", Toast.LENGTH_LONG).show()
            }
            User.Validation.AUTH_EMPTY -> {
                Toast.makeText(this, "Auth failed to connect", Toast.LENGTH_LONG).show()
            }
            User.Validation.INVALID_INPUTS -> {
                Toast.makeText(this, "Invalid Inputs", Toast.LENGTH_LONG).show()
            }
            User.Validation.SUCESS_LOGG_IN -> {
                Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
            }
            User.Validation.DATABASE_UPDATE_FAIL -> {
                Toast.makeText(this, "Failed to communicate with database", Toast.LENGTH_LONG).show()
            }
        }
        if (user!!.hasErrors) {
            Toast.makeText(this@CreateAccount, user!!.errors[0], Toast.LENGTH_LONG).show()
        }

    }




}
