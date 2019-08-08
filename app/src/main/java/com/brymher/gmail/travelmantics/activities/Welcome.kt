package com.brymher.gmail.travelmantics.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brymher.gmail.travelmantics.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_welcome.*

class Welcome : AppCompatActivity(), OnCompleteListener<AuthResult> {

    var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)


        init()
    }


    private fun init() {
        initUser()
    }

    fun enableSignInControls() {
        accessControls?.visibility = View.VISIBLE


        emailSignIn?.setOnClickListener {
            startActivity(CreateAccount::class.java)
        }

    }

    private fun startActivity(clazz: Class<CreateAccount>) {
        startActivity(Intent(baseContext, clazz))
    }

    fun startActivity() {}

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_place -> {
                startActivity(CreateAccount::class.java)
                true
            }
            else -> true
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