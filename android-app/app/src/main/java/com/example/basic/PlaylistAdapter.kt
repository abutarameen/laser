package com.example.basic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlaylistAdapter(
    private val context: Context,
    private val songs: MutableList<Song>,
    private val onPlay: (Song) -> Unit
) : RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.titleView)
        val playButton: Button = view.findViewById(R.id.playButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_song, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songs[position]
        holder.titleView.text = song.title
        holder.playButton.setOnClickListener { onPlay(song) }
    }

    override fun getItemCount(): Int = songs.size

    fun addSongs(newSongs: List<Song>) {
        val start = songs.size
        songs.addAll(newSongs)
        notifyItemRangeInserted(start, newSongs.size)
    }
}
