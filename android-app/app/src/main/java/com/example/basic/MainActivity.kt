package com.example.basic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

/**
 * Main activity displaying a playlist and search field.
 */
class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()
    private lateinit var adapter: PlaylistAdapter
    private val songs = mutableListOf<Song>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchField: EditText = findViewById(R.id.searchField)
        val searchButton: Button = findViewById(R.id.searchButton)
        val playlistView: RecyclerView = findViewById(R.id.playlistView)
        adapter = PlaylistAdapter(this, songs) { playSong(it) }
        playlistView.layoutManager = LinearLayoutManager(this)
        playlistView.adapter = adapter

        searchButton.setOnClickListener {
            val query = searchField.text.toString()
            if (query.isNotBlank()) {
                searchSongs(query)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun playSong(song: Song) {
        val intent = Intent(this, MusicService::class.java).apply {
            data = song.streamUrl
            putExtra("title", song.title)
        }
        startService(intent)
    }

    private fun searchSongs(query: String) {
        // Basic SoundCloud search using public API
        CoroutineScope(Dispatchers.IO).launch {
            val url = "https://api.soundcloud.com/tracks?client_id=YOUR_CLIENT_ID&q=" +
                Uri.encode(query)
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@launch
            val results = parseTracks(body)
            withContext(Dispatchers.Main) {
                adapter.addSongs(results)
            }
        }
    }

    private fun parseTracks(json: String): List<Song> {
        val list = mutableListOf<Song>()
        val arr = JSONArray(json)
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            val title = obj.getString("title")
            val streamUrl = obj.getString("stream_url") + "?client_id=YOUR_CLIENT_ID"
            list.add(Song(title, Uri.parse(streamUrl)))
        }
        return list
    }
}
