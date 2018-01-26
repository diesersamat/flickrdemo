package me.sgayazov.flickrdemo.activity

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.app.NavUtils
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_photo_detail.*
import me.sgayazov.flickrdemo.R
import me.sgayazov.flickrdemo.domain.Photo
import me.sgayazov.flickrdemo.networking.ImageLoader

const val MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1000

class PhotoDetailActivity : AppCompatActivity() {

    //it would be even better to pass only photo's id in extras and request full object from network/database
    private lateinit var photo: Photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        photo = intent.getParcelableExtra(PHOTO_EXTRA)
        go_button.setOnClickListener {
            openPhotoInBrowser()
        }
        ImageLoader.loadImage(this, photo.url_c, image)

    }

    private fun openPhotoInBrowser() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(generateLinkToFlickr()))
        startActivity(browserIntent)
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
                    downloadImage()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORAGE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                downloadWithPermission()
            }
        }
    }

    private fun downloadImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_STORAGE)
        } else {
            downloadWithPermission()
        }
    }

    //maybe move this to "DownloadManager", "FileController" or something like this to make it more abstract
    private fun downloadWithPermission() {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val uri = Uri.parse(photo.url_c)
        val request = DownloadManager.Request(uri)
        request.setDescription(getString(R.string.downloading_desc))
        request.setTitle(getString(R.string.downloading_title))
        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.lastPathSegment)
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    private fun shareImage() {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
        i.putExtra(Intent.EXTRA_TEXT, generateLinkToFlickr())
        startActivity(Intent.createChooser(i, getString(R.string.share_image)))
    }

    private fun generateLinkToFlickr() = "https://www.flickr.com/photos/${photo.owner}/${photo.id}"
}
