package com.brymher.gmail.travelmantics.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.brymher.gmail.travelmantics.models.Place
import kotlinx.android.synthetic.main.activity_create_place.*


class CreatePlace : AppCompatActivity() {

    var place: Place = Place()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_place)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.admin, menu)

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_place -> {
                savePlace()
                return true
            }

            else -> true
        }
    }

    private fun savePlace() {
        Place().createPlace(
            com.brymher.gmail.travelmantics.data.Place(
                name = pName.text.toString(),
                price = Integer.parseInt(pPrice.text.toString()),
                description = pDesc.text.toString()
            )
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 42 && resultCode == RESULT_OK) {
            data?.let { image ->
                val imageUri = image.data

                Place().apply {

                    uploadPlaceImage(imageUri)
                }


            }

        }
    }

}
