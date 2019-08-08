package com.brymher.gmail.travelmantics.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.brymher.gmail.travelmantics.lib.inputs.Email
import com.brymher.gmail.travelmantics.lib.inputs.Password
import com.brymher.gmail.travelmantics.models.User
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccount : Base(R.layout.activity_create_account) {

    override fun init(savedInstanceState: Bundle?) {
        signUp?.setOnClickListener {
            val user = User()

            Toast.makeText(this@CreateAccount, password?.text ?: "", Toast.LENGTH_LONG).show()


            user.apply {

                val email = Email(email?.text.toString())
                val pwd = Password(password?.text.toString(), passwordR?.text.toString())

                if (validateInputs(email, pwd)) {
                    user.register(email, pwd, {
                        AlertDialog.Builder(this@CreateAccount).apply {
                            setTitle("Logged In")

                            show()
                        }
                    }) {

                    }
                } else {
                    Toast.makeText(this@CreateAccount, errors[0], Toast.LENGTH_LONG).show()
                }
            }


        }

        signIn?.setOnClickListener {
            startActivity(Intent(this, Welcome::class.java))
        }
    }

}
