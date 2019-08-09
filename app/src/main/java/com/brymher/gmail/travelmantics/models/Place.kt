package com.brymher.gmail.travelmantics.models

import android.net.Uri
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.brymher.gmail.travelmantics.data.Place as DPlace

class Place : FireBaseModel() {
    private val STORAGE = FirebaseStorage.getInstance()
    private val STORAGE_REG get() = STORAGE.reference.child("place_pictures")

    fun createPlace(place: DPlace, success: (Void) -> Unit, failure: (Exception) -> Unit) {

        dbRef.apply {
            child("places")
                .push()
                .setValue(place)
                .apply {
                    addOnSuccessListener(success)
                    addOnFailureListener(failure)
                }
        }
        /* if (place.id == null) {
             dbRef.child("places").push().setValue(place)
         } else {
             dbRef.child(place.id).setValue(place)
         }*/
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