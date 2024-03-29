package com.brymher.gmail.travelmantics.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.brymher.gmail.travelmantics.activities.R
import com.brymher.gmail.travelmantics.models.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.brymher.gmail.travelmantics.data.Place as DPlace
import com.google.firebase.database.DatabaseError
import com.squareup.picasso.Picasso
import org.json.JSONObject


class Place(val context: AppCompatActivity) : RecyclerView.Adapter<Place.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.place_row, parent, false))
    }

    var places = arrayOf<DPlace>()

    override fun getItemCount(): Int = places.size


    init {
        val placeModel = com.brymher.gmail.travelmantics.models.Place()
        placeModel.dbRef.child("places").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(e: DatabaseError) {
                Toast.makeText(this@Place.context, e.message, Toast.LENGTH_LONG).show()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(snapshot: DataSnapshot, id: String?) {
                snapshot.getValue(DPlace::class.java)?.let {
                    it.id = snapshot.key
                    Log.d("Place", it.name)
                    places += it
                    notifyItemInserted(places.size - 1)
                }
            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }

        })

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(places[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val place_image = view.findViewById<ImageView>(R.id.place_image)
        val place_name = view.findViewById<TextView>(R.id.place_name)
        val place_price = view.findViewById<TextView>(R.id.place_price)
        val place_description = view.findViewById<TextView>(R.id.place_desc)

        init {
            view.setOnClickListener {
                val user = User()

                if (user.isAdmin) {

                }
            }
        }

        fun bind(place: com.brymher.gmail.travelmantics.data.Place) {
            place_name.text = place.name
            place_description.text = place.desc
            place_price.text = place.price.toString()

            place.image?.let {
                if (it.isNotEmpty()) {
                    Picasso.get()
                        .load(place.image)
                        .resize(160, 160)
                        .centerCrop()
                        .into(place_image)
                }
            }
        }

    }

}