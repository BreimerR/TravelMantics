package com.brymher.gmail.travelmantics.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brymher.gmail.travelmantics.activities.R
import com.brymher.gmail.travelmantics.models.User
import com.squareup.picasso.Picasso


class Place(val items: Array<com.brymher.gmail.travelmantics.data.Place>) : RecyclerView.Adapter<Place.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.place_row, parent, false))
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val place_image = view.findViewById<ImageView>(R.id.place_image)
        val place_name = view.findViewById<TextView>(R.id.place_image)
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
            place_description.text = place.description
            place_price.text = place.price.toString()

            Picasso.get()
                .load(place.profile_image)
                .resize(160, 160)
                .centerCrop()
                .into(place_image)
        }

    }

}