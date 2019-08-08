package com.brymher.gmail.travelmantics.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.brymher.gmail.travelmantics.models.User
import com.google.android.gms.tasks.OnCompleteListener
import kotlinx.android.synthetic.main.activity_welcome.*

class Welcome : Base(R.layout.activity_welcome), OnCompleteListener<AuthResult> {

    var user: User? = null

    override fun init(savedInstanceState: Bundle?) {
        initUser()
    }

    private fun enableSignInControls() {
        accessControls?.visibility = View.VISIBLE

        emailSignIn?.setOnClickListener {
            startActivity(CreateAccount::class.java)
        }
    }

    private fun initUser() {
        val user = User()

        when {
            !user.isSignedIn -> {
                enableSignInControls()
            }
            !user.isVerified -> {
                enableSignInControls()
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