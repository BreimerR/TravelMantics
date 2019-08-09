package com.brymher.gmail.travelmantics.models

import android.net.Uri
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.brymher.gmail.travelmantics.data.Place as DPlace

class Place : FireBaseModel() {

    var places = arrayOf<Place>()

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

    fun getPlaces(listener: ChildEventListener): ArrayList<DPlace> {
        val places = arrayListOf<DPlace>()

        dbRef.apply {
            child("places")
        }.addChildEventListener(listener)


        return places
    }

    private fun updateDisplay(snapshot: DataSnapshot) {

    }


}