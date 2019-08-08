package com.brymher.gmail.travelmantics.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

abstract class FireBaseModel : Model() {
    val database = FirebaseDatabase.getInstance()

    val dbRef get() = database.reference

}