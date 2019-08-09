package com.brymher.gmail.travelmantics.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


abstract class FireBaseAuthModel : FireBaseModel() {
    lateinit var authState: FirebaseAuth.AuthStateListener

    val auth = FirebaseAuth.getInstance()
}