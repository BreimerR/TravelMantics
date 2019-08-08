package com.brymher.gmail.travelmantics.models


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.brymher.gmail.travelmantics.data.User as AUser
import com.brymher.gmail.travelmantics.lib.inputs.Validatable
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

open class User : FireBaseAuthModel() {


    val currentUser get() = auth?.currentUser

    val isVerified: Boolean
        get() {
            currentUser?.apply {
                return isEmailVerified
            }

            return false
        }

    val isSignedIn: Boolean
        get() {
            auth?.currentUser?.apply {
                return isEmailVerified
            }

            return false
        }

    val sendVerification: Unit
        get() {
            auth.currentUser?.sendEmailVerification()
        }

    var isAdmin: Boolean = false

    fun <T : Validatable<String>, V : Validatable<String>, N : Validatable<String>> register(
        email: T,
        password: V,
        name: N,
        success: () -> Unit,
        fail: (Int) -> Unit
    ) {
        if (validateInputs(email, password)) {
            if (auth != null) {
                createUser(auth, email.value, password.value, name = name.value, success = success, fail = fail)
            } else {
                fail(Validation.AUTH_EMPTY)
            }
        } else fail(Validation.INVALID_INPUTS)
    }


    private fun createUser(
        auth: FirebaseAuth,
        email: String,
        password: String,
        name: String,
        type: Boolean = false,
        success: () -> Unit,
        fail: (Int) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val uid = auth.currentUser?.uid as String

                dbRef.apply {
                    child("users")
                        .child(uid)
                        .setValue(
                            AUser(
                                uid,
                                name,
                                type
                            )
                        ).addOnSuccessListener {
                            success()
                            sendVerification
                            auth.signOut()
                        }.addOnFailureListener {
                            fail(Validation.DATABASE_UPDATE_FAIL)
                            auth.signOut()
                        }

                }

            }

            if (it.isCanceled) {
                fail(Validation.AUTH_CANCELED)
            }
        }
    }

    fun readUser(uid: String) {
        val u = dbRef.child("users").child(uid)

        val listener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                isAdmin = true
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }
        u.addChildEventListener(listener)
    }

    private fun writeNewPost(id: String, admin: Boolean, name: String, profile_image: String) {
        val key = dbRef.child("users").push().key
        if (key == null) {
            Log.w("User.writeNewPost", "Couldn't get push key for posts")
            return
        }

        val post = PostUser(id, admin, name, profile_image)
        val postValues = post.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/posts/$key"] = postValues
        childUpdates["/user-posts/$id/$key"] = postValues

        dbRef.updateChildren(childUpdates)
    }

    fun <V, T : Validatable<V>> validateInputs(vararg inputs: T): Boolean {
        for (input in inputs) {
            if (!input.isValid) {
                input.error?.let {
                    errors += it
                }
            }
        }

        return !hasErrors
    }

    /*  fun googleRegister() {
          val googleReg = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
              .requestIdToken(getString(R.string.default_web_client_id))
              .requestEmail()
              .build()
      }*/

    object Validation {
        const val AUTH_CANCELED = 0
        const val AUTH_EMPTY = 1
        const val INVALID_INPUTS = 2
        const val SUCESS_LOGG_IN = 3
        const val DATABASE_UPDATE_FAIL = 4

    }

    /* class GoogleSignInActivity : View.OnClickListener {

         // [START declare_auth]
         private lateinit var auth: FirebaseAuth
         // [END declare_auth]

         private lateinit var googleSignInClient: GoogleSignInClient

         fun onCreate(savedInstanceState: Bundle?) {

             // Button listeners
             signInButton.setOnClickListener(this)
             signOutButton.setOnClickListener(this)
             disconnectButton.setOnClickListener(this)

             // [START config_signin]
             // Configure Google Sign In
             val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.default_web_client_id))
                 .requestEmail()
                 .build()
             // [END config_signin]

             googleSignInClient = GoogleSignIn.getClient(this, gso)

             // [START initialize_auth]
             // Initialize Firebase Auth
             auth = FirebaseAuth.getInstance()
             // [END initialize_auth]
         }

         // [START on_start_check_user]
         public override fun onStart() {
             super.onStart()
             // Check if user is signed in (non-null) and update UI accordingly.
             val currentUser = auth.currentUser
             updateUI(currentUser)
         }
         // [END on_start_check_user]

         // [START onactivityresult]
         public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
             super.onActivityResult(requestCode, resultCode, data)

             // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
             if (requestCode == RC_SIGN_IN) {
                 val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                 try {
                     // Google Sign In was successful, authenticate with Firebase
                     val account = task.getResult(ApiException::class.java)
                     firebaseAuthWithGoogle(account!!)
                 } catch (e: ApiException) {
                     // Google Sign In failed, update UI appropriately
                     Log.w(TAG, "Google sign in failed", e)
                     // [START_EXCLUDE]
                     updateUI(null)
                     // [END_EXCLUDE]
                 }
             }
         }
         // [END onactivityresult]

         // [START auth_with_google]
         private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
             Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
             // [START_EXCLUDE silent]
             showProgressDialog()
             // [END_EXCLUDE]

             val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
             auth.signInWithCredential(credential)
                 .addOnCompleteListener(this) { task ->
                     if (task.isSuccessful) {
                         // Sign in success, update UI with the signed-in user's information
                         Log.d(TAG, "signInWithCredential:success")
                         val user = auth.currentUser
                         updateUI(user)
                     } else {
                         // If sign in fails, display a message to the user.
                         Log.w(TAG, "signInWithCredential:failure", task.exception)
                         Snackbar.make(main_layout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                         updateUI(null)
                     }

                     // [START_EXCLUDE]
                     hideProgressDialog()
                     // [END_EXCLUDE]
                 }
         }
         // [END auth_with_google]

         // [START signin]
         private fun signIn() {
             val signInIntent = googleSignInClient.signInIntent
             startActivityForResult(signInIntent, RC_SIGN_IN)
         }
         // [END signin]

         private fun signOut() {
             // Firebase sign out
             auth.signOut()

             // Google sign out
             googleSignInClient.signOut().addOnCompleteListener(this) {
                 updateUI(null)
             }
         }

         private fun revokeAccess() {
             // Firebase sign out
             auth.signOut()

             // Google revoke access
             googleSignInClient.revokeAccess().addOnCompleteListener(this) {
                 updateUI(null)
             }
         }

         private fun updateUI(user: FirebaseUser?) {
             hideProgressDialog()
             if (user != null) {
                 status.text = getString(R.string.google_status_fmt, user.email)
                 detail.text = getString(R.string.firebase_status_fmt, user.uid)

                 signInButton.visibility = View.GONE
                 signOutAndDisconnect.visibility = View.VISIBLE
             } else {
                 status.setText(R.string.signed_out)
                 detail.text = null

                 signInButton.visibility = View.VISIBLE
                 signOutAndDisconnect.visibility = View.GONE
             }
         }

         override fun onClick(v: View) {
             val i = v.id
             when (i) {
                 R.id.signInButton -> signIn()
                 R.id.signOutButton -> signOut()
                 R.id.disconnectButton -> revokeAccess()
             }
         }

         companion object {
             private const val TAG = "GoogleActivity"
             private const val RC_SIGN_IN = 9001
         }
     }*/
}