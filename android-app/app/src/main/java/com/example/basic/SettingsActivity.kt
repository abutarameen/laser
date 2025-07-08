package com.example.basic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Simple activity that opens SoundCloud or VK login in the browser.
 */
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // For simplicity just start the SoundCloud OAuth page.
        val url = "https://soundcloud.com/connect"
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        finish()
    }
}
