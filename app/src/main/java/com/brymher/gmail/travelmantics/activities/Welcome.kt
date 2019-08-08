package com.brymher.gmail.travelmantics.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.brymher.gmail.travelmantics.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_welcome.*

class Welcome : Base(R.layout.activity_welcome), OnCompleteListener<AuthResult> {

    var user: User? = null

    override fun init(savedInstanceState: Bundle?) {
        initUser()
        enableSignInControls()
    }

    private fun enableSignInControls() {
        accessControls?.visibility = View.VISIBLE

        val signInAction: (View) -> Unit = {
            startActivity(CreateAccount::class.java)
        }

        emailSignIn?.setOnClickListener(signInAction)
        emailSignInIcon?.setOnClickListener(signInAction)
        emailSignInText?.setOnClickListener(signInAction)


        googleSignIn?.setOnClickListener {
            //val googleSizeException = GoogleSignIn.getClient(this,)
        }

    }

    private fun initUser() {
        val user = User()

        when {
            !user.isSignedIn -> {

            }

            !user.isVerified -> {
                AlertDialog.Builder(this).apply {
                    setTitle("Resend Verification")
                    setPositiveButton("Send") { _, _ ->

                    }

                    setNegativeButton("") { _, _ ->

                    }

                    show()
                }
            }
            else -> {
                startActivity(Intent(this, Places::class.java))
            }
        }
    }

    override fun onComplete(task: Task<AuthResult>) {
        if (task.isComplete)
            Toast.makeText(this, "complete", Toast.LENGTH_LONG).show()

        if (task.isCanceled)
            Toast.makeText(this, "canceled", Toast.LENGTH_LONG).show()

        if (task.isSuccessful)
            Toast.makeText(this, "successful", Toast.LENGTH_LONG).show()


    }
}