package com.brymher.gmail.travelmantics.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.brymher.gmail.travelmantics.models.User

class Places : AppCompatActivity() {

    val user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val insertMenu = menu?.findItem(R.id.add_place);
        insertMenu?.isVisible = user.isAdmin
        return true
    }
}
