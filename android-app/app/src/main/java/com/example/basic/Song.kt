package com.example.basic

import android.net.Uri

/**
 * Model representing a single song.
 */
data class Song(
    val title: String,
    val streamUrl: Uri
)
