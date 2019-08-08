package com.brymher.gmail.travelmantics.models

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.OnSuccessListener
import com.brymher.gmail.travelmantics.data.Place as DPlace

class Place : FireBaseModel() {
    val storage = FirebaseStorage.getInstance()
    mStorageRef = mStorage.getReference().child("deals_pictures")

    fun createPlace(place: DPlace) {
        if (place.id == null) {
            dbRef.push().setValue(place)
        } else {
            dbRef.child(place.id).setValue(place)
        }
    }

    fun uploadPlaceImage(imageUri: Uri?, context: Context) {
        val ref = FirebaseUtil.mStorageRef.child(imageUri.getLastPathSegment());dbRef.child(imageUri!!.lastPathSegment)

        dbRef.
        dbRef.putFile(imageUri).addOnSuccessListener(context,
            OnSuccessListener<Any> { taskSnapshot ->
                val url = ref.getDownloadUrl().toString()
                //String url = taskSnapshot.getDownloadUrl().toString();
                val pictureName = taskSnapshot.getStorage().getPath()
                deal.setImageUrl(url)
                deal.setImageName(pictureName)
                Log.d("Url: ", url)
                Log.d("Name", pictureName)
                showImage(url)
            })
    }
}