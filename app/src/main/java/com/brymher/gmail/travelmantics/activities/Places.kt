package com.brymher.gmail.travelmantics.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import com.brymher.gmail.travelmantics.models.Place
import com.brymher.gmail.travelmantics.data.Place as DPlace
import com.brymher.gmail.travelmantics.adapters.Place as PlaceAdapter
import com.brymher.gmail.travelmantics.models.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class Places : Base(R.layout.activity_places) {
    override val menu: Int? = R.menu.main

    val user = User()


    var places = arrayListOf<DPlace>()

    override fun init(savedInstanceState: Bundle?) {
        initAdapter()
    }

    fun initAdapter() {
        val places =  findViewById<RecyclerView>(R.id.places)
        places.adapter = PlaceAdapter(this)

    }

    private fun updateDisplay(snapshot: DataSnapshot) {
        snapshot.getValue(DPlace::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return onCreateOptionsMenu(menu) {
            val insertMenu = menu?.findItem(R.id.add_place)
            insertMenu?.isVisible = user.isAdmin
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.sign_out -> {
                user.signOut()
                startActivity(this, Welcome::class.java)
                true
            }

            R.id.add_place -> {
                startActivity(this, CreatePlace::class.java)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
