package com.brymher.gmail.travelmantics.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PostUser(
    var id: String? = "",
    var admin: Boolean = false,
    var name: String? = "",
    var profile_image: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "admin" to admin,
            "name" to name,
            "profile_image" to profile_image
        )
    }
}