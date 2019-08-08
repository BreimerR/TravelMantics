package com.brymher.gmail.travelmantics.models

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.brymher.gmail.travelmantics.data.Place as DPlace

class Place : FireBaseModel() {
    private val STORAGE = FirebaseStorage.getInstance()
    private val STORAGE_REG get() = STORAGE.reference.child("place_pictures")

    fun createPlace(place: DPlace) {
        if (place.id == null) {
            dbRef.push().setValue(place)
        } else {
            dbRef.child(place.id).setValue(place)
        }
    }

    fun uploadPlaceImage(imageUri: Uri?, onSuccess: (UploadTask.TaskSnapshot, String) -> Unit) {

        imageUri?.apply {
            with(STORAGE_REG.child(lastPathSegment!!.toString())) {

                putFile(imageUri).addOnSuccessListener {
                    onSuccess(it, this.downloadUrl.toString())
                }
            }


        }


    }
}