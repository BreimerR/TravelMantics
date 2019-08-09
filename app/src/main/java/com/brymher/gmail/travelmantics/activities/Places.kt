package com.brymher.gmail.travelmantics.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.brymher.gmail.travelmantics.models.User

class Places : Base(R.layout.activity_places) {
    override val menu: Int? = R.menu.main

    val user = User()

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return onCreateOptionsMenu(menu) {
            val insertMenu = menu?.findItem(R.id.add_place)
            insertMenu?.isVisible = user.isAdmin

        }
    }

}
