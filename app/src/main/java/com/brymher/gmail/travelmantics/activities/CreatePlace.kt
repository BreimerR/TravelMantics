package com.brymher.gmail.travelmantics.activities

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.content.res.Resources

import com.squareup.picasso.Picasso
import com.brymher.gmail.travelmantics.models.Place
import com.brymher.gmail.travelmantics.data.Place as DPlace
import kotlinx.android.synthetic.main.activity_create_place.*


class CreatePlace : Base(R.layout.activity_create_place) {

    override val menu: Int? = R.menu.admin

    private val PIC_RES = 42

    var place: Place = Place()

    var dPlace: DPlace = DPlace(name = "")

    var imgData: Uri? = null

    override fun init(savedInstanceState: Bundle?) {
        initDisplayContent(savedInstanceState)

    }

    private fun initDisplayContent(savedInstanceState: Bundle?) {
        initEvents()
    }

    private fun initEvents() {
        pOverlayImage?.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/jpeg"
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Insert Picture"
                ), PIC_RES
            )
        }
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
            DPlace(
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
                // prevent upload of un used image
                imgData = image.data

                Place().apply {
                    uploadPlaceImage(imageUri) { _, url ->
                        dPlace.profile_image = url
                        showImage(url)
                    }
                }
            }

        }
    }

    private fun showImage(url: String) {
        if (url.isNotEmpty()) {

            val width = Resources.getSystem().displayMetrics.widthPixels

            Picasso.get()
                .load(url)
                .resize(width, width * 2 / 3)
                .centerCrop()
                .into(pImage)
        }
    }

}
