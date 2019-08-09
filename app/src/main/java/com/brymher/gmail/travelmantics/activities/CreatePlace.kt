package com.brymher.gmail.travelmantics.activities

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.content.res.Resources
import android.widget.Toast

import com.squareup.picasso.Picasso
import com.brymher.gmail.travelmantics.models.Place
import com.brymher.gmail.travelmantics.models.User
import com.brymher.gmail.travelmantics.data.Place as DPlace
import kotlinx.android.synthetic.main.activity_create_place.*


class CreatePlace : Base(R.layout.activity_create_place) {

    val user = User()

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_place -> {
                savePlace()
                return true
            }

            R.id.sign_out -> {
                user.signOut()
                startActivity(this, Welcome::class.java)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun savePlace() {
        Place().createPlace(
            DPlace(
                name = pName.text.toString(),
                price = Integer.parseInt(pPrice.text.toString()),
                desc = pDesc.text.toString()
            ), {
                Toast.makeText(this, "Uploaded Place", Toast.LENGTH_LONG).show()
            }
        ) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }

        return

        // upload image first then save the place data
        Place().apply {
            uploadPlaceImage(imgData) { _, url ->
                dPlace.image = url
                showImage(url)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {
            RESULT_OK -> {
                when (requestCode) {
                    42 -> {
                        data?.let { image ->
                            val imageUri = image.data
                            // prevent upload of un used image
                            imgData = image.data
                            showImage()

                        }
                    }
                }
            }

            else -> Toast.makeText(this, "Failed to load Request", Toast.LENGTH_LONG).show()
        }
    }

    private fun showImage() {
        imgData?.let {
            val width = Resources.getSystem().displayMetrics.widthPixels

            Picasso.get()
                .load(it)
                .resize(width, width * 2 / 3)
                .centerCrop()
                .into(pImage)
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
        } else Toast.makeText(this, "Image Url not Corect", Toast.LENGTH_LONG).show()
    }

}
