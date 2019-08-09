package com.brymher.gmail.travelmantics.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Place(
    var id: String? = null,
    val name: String?,
    val price: Int? = 0,
    var image: String? = "",
    val desc: String? = null
) :
    Parcelable {
    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.apply {
            writeString(id)
            writeString(name)
            writeInt(price ?: 0)
            writeString(image)
            writeString(desc)
        }
    }


    constructor() : this(null, null, null)


    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<Place> {
        override fun createFromParcel(parcel: Parcel?): Place {

            parcel?.apply {
                return Place(
                    readString() ?: "",
                    readString() ?: "",
                    readInt(),
                    readString() ?: "",
                    readString() ?: ""
                )
            }


            return Place(name = "Item Name")
        }

        override fun newArray(size: Int): Array<Place> {
            return arrayOf()
        }

    }

}