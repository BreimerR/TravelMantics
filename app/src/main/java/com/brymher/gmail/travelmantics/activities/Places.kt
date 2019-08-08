package com.brymher.gmail.travelmantics.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.brymher.gmail.travelmantics.models.User

class Places : Base(R.layout.activity_places) {

    val user = User()

    override fun init(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val insertMenu = menu?.findItem(R.id.add_place);
        insertMenu?.isVisible = user.isAdmin
        return true
    }
}
