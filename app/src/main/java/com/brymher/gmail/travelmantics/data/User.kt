package com.brymher.gmail.travelmantics.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(val id: String, val name: String, val admin: Boolean, val profile_image: String = "")