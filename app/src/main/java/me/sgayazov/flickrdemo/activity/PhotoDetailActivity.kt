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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val photo = intent.getParcelableExtra<Photo>(PHOTO_EXTRA)
            ImageLoader.loadImage(this, photo.url_m, image)
        }
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
                else -> super.onOptionsItemSelected(item)
            }
}
