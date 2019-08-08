package com.brymher.gmail.travelmantics.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.brymher.gmail.travelmantics.lib.inputs.Email
import com.brymher.gmail.travelmantics.lib.inputs.Password
import com.brymher.gmail.travelmantics.models.User
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        init(savedInstanceState)
    }



    private fun init(savedInstanceState: Bundle?) {
        signIn?.setOnClickListener {
            val user = User()

            Toast.makeText(this@CreateAccount, password?.text ?: "", Toast.LENGTH_LONG).show()


            user.apply {

                val email = Email(email?.text.toString())
                val pwd = Password(password?.text.toString(), passwordR?.text.toString())

                if (validateInputs(email, pwd)) {
                    user.register(email, pwd, {
                        AlertDialog.Builder(this@CreateAccount).apply {
                            setTitle("Logged In")
                            user.kind
                            show()
                        }
                    }) {

                    }
                } else {
                    Toast.makeText(this@CreateAccount, errors[0], Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
