package com.brymher.gmail.travelmantics.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class Welcome : AppCompatActivity(), OnCompleteListener<AuthResult> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val auth = FirebaseAuth.getInstance()

        auth?.createUserWithEmailAndPassword("brymher@gmail.com", "password")?.addOnCompleteListener(this)
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