package com.brymher.gmail.travelmantics.models


import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ChildEventListener
import com.brymher.gmail.travelmantics.data.User as AUser
import com.brymher.gmail.travelmantics.lib.inputs.Validatable
import com.brymher.gmail.travelmantics.lib.inputs.StringValidatable

open class User : FireBaseAuthModel() {


    var error: String? = null

    val hasError: Boolean
        get() {
            return error != null
        }

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

    val sendVerification: Task<Void>?
        get() {
            return auth.currentUser?.sendEmailVerification()
        }

    var isAdmin: Boolean = true

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
        type: Boolean = true,
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
                            auth.signOut()
                            sendVerification?.apply {
                                addOnSuccessListener {

                                }

                                addOnFailureListener { e ->
                                    error = e.message
                                }
                            }
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


    /*should return true for security of event being handled*/
    fun <T : StringValidatable, V : StringValidatable> signIn(
        email: T,
        password: V,
        complete: (Task<AuthResult>) -> Unit,
        fail: (Int) -> Boolean

    ) {
        if (validateInputs(email, password)) {
            auth.signInWithEmailAndPassword(email.value, password.value).addOnCompleteListener(complete)
        } else if (fail(Validation.INVALID_INPUTS)) {
            throw Exception("Unhandled Input data")
        }
    }

    val removeStateListenre: Boolean
        get() {
            auth.removeAuthStateListener(authState)
            return true
        }


    fun signOut() {
        auth.signOut()
    }

    object Validation {
        const val AUTH_CANCELED = 0
        const val AUTH_EMPTY = 1
        const val INVALID_INPUTS = 2
        const val SUCESS_LOGG_IN = 3
        const val DATABASE_UPDATE_FAIL = 4
        const val USER_ALREADY_EXISTS = 5
    }
}