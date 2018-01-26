package me.sgayazov.flickrdemo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_photo_detail.*
import me.sgayazov.flickrdemo.R
import me.sgayazov.flickrdemo.domain.Photo
import me.sgayazov.flickrdemo.networking.ImageLoader


class PhotoDetailActivity : AppCompatActivity() {

    //it would be even better to pass only photo's id in extras and request full object from network/database
    private lateinit var photo: Photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        photo = intent.getParcelableExtra(PHOTO_EXTRA)
        ImageLoader.loadImage(this, photo.url_o, image)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detailed_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    NavUtils.navigateUpTo(this, Intent(this, PhotosListActivity::class.java))
                    true
                }
                R.id.menu_item_share -> {
                    shareImage()
                    true
                }
                R.id.menu_item_download -> {
                    NavUtils.navigateUpTo(this, Intent(this, PhotosListActivity::class.java))
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun shareImage() {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
        i.putExtra(Intent.EXTRA_TEXT, photo.url_o)
        startActivity(Intent.createChooser(i, getString(R.string.share_image)))
    }
}
