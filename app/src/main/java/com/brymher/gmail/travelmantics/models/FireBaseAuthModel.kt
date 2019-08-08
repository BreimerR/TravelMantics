package com.brymher.gmail.travelmantics.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


abstract class FireBaseAuthModel : FireBaseModel() {
    val auth = FirebaseAuth.getInstance()
}