package com.brymher.gmail.travelmantics.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.brymher.gmail.travelmantics.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_welcome.*

class Welcome : DialogActivity(R.layout.activity_welcome), OnCompleteListener<AuthResult> {

    lateinit var user: User

    override fun init(savedInstanceState: Bundle?) {
        initUser()
        enableSignInControls()
    }

    private fun enableSignInControls() {
        accessControls?.visibility = View.VISIBLE

        val signInAction: (View) -> Unit = {
            startActivity(this, CreateAccount::class.java)
        }

        emailSignIn?.setOnClickListener(signInAction)
        emailSignInIcon?.setOnClickListener(signInAction)
        emailSignInText?.setOnClickListener(signInAction)


        googleSignIn?.setOnClickListener {
            //val googleSizeException = GoogleSignIn.getClient(this,)
        }

    }

    private fun initUser() {
        user = User().apply {
            when {

                isSignedIn -> {
                    if (!isVerified) showDialog {
                        setTitle("Resend Verification")
                        setPositiveButton("Send") { _, _ ->
                            // send email verification details
                            user.apply {
                                sendVerification
                            }
                        }

                        setNegativeButton("Register account") { _, _ ->
                            startActivity(this@Welcome, CreateAccount::class.java)
                        }

                        setNeutralButton("Cancel") { it, _ ->
                            it.dismiss()
                        }
                        show()
                    }
                }


                else -> startActivity(Intent(this@Welcome, Places::class.java))

            }


        }
    }

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isComplete)
            Toast.makeText(this, "complete", Toast.LENGTH_LONG).show()

        when {
            task.isCanceled -> Toast.makeText(this, "canceled", Toast.LENGTH_LONG).show()
            task.isSuccessful -> Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()
        }


    }
}