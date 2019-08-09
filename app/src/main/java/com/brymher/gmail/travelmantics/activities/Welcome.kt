package com.brymher.gmail.travelmantics.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.brymher.gmail.travelmantics.lib.inputs.Email
import com.brymher.gmail.travelmantics.lib.inputs.Password
import com.brymher.gmail.travelmantics.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_welcome.*


class Welcome : DialogActivity(R.layout.activity_welcome), OnCompleteListener<AuthResult> {

    lateinit var user: User

    var dialog: AlertDialog? = null

    override fun init(savedInstanceState: Bundle?) {
        initUser()
    }

    val signInAction: (View?) -> Unit = {

        dialog = AlertDialog.Builder(this, R.style.AppTheme_AlertDialog).apply {
            val view: View = layoutInflater.inflate(R.layout.sign_in, null)
            val sin = view.findViewById<Button>(R.id.signInBtn)
            val bdy = view.findViewById<ConstraintLayout>(R.id.signInBody)

            sin.setOnClickListener {
                signInUser(view)
            }

            bdy.setOnClickListener {
                dialog?.dismiss()
            }

            setView(view)
        }.show()

    }

    private fun enableSignInControls() {
        accessControls?.visibility = View.VISIBLE


        emailSignInIcon.setOnClickListener(signInAction)
        emailSignInText.setOnClickListener(signInAction)


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
                    } else startActivity(Intent(this@Welcome, Places::class.java))
                }

                else -> enableSignInControls()
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

    fun regUser(it: View) {
        dialog?.dismiss()
        startActivity(this, CreateAccount::class.java)
    }

    fun signInUser(view: View) {
        val email = view.findViewById<TextInputEditText>(R.id.email)?.text.toString()
        val password = view.findViewById<TextInputEditText>(R.id.pwd)

        user.signIn(Email(email), Password.SignIn(password?.text.toString()), {
            Toast.makeText(this, "Signing in...", Toast.LENGTH_LONG).show()
            it.addOnSuccessListener { auth ->
                val usr = auth.user

                if (usr.isEmailVerified) {
                    Toast.makeText(this, "Signed In", Toast.LENGTH_LONG).show()
                    dialog?.dismiss()
                    startActivity(this, Places::class.java)
                } else {
                    // sign out the user
                    FirebaseAuth.getInstance().signOut()
                    dialog?.dismiss()

                    showDialog {
                        setTitle("Email verification")
                        setMessage("Resend email verification")

                        setPositiveButton("Send") { dial, _ ->
                            Toast.makeText(this@Welcome, "Sending email verification...", Toast.LENGTH_LONG).show()
                            usr.sendEmailVerification()
                            dial.dismiss()
                        }

                        setNegativeButton("Cancel") { dial, _ ->
                            Toast.makeText(this@Welcome, "Sending email verification...", Toast.LENGTH_LONG).show()
                            dial.dismiss()
                        }

                        setNeutralButton("Register Acc") { dial, _ ->
                            startActivity(this@Welcome, CreateAccount::class.java)
                            dial.dismiss()
                        }
                    }
                }
            }

            it.addOnFailureListener { e ->
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }

        }) {
            true
        }
    }

    fun bodyClick(it: View) {
        dialog?.dismiss()
    }

    override fun onStop() {
        super.onStop()

    }
}