package com.pattern.musicapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pattern.musicapp.databinding.SongItemBinding

class SongAdapter: RecyclerView.Adapter<SongAdapter.SongHolder>() {

    val songList = ArrayList<Song>()

    class SongHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = SongItemBinding.bind(item)
        fun bind(song: Song) = with(binding){
            imageView.setImageResource(song.imageId)
            textTitle.text = song.title
            textSubtitle.text = song.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
        return SongHolder(view)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        holder.bind(songList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSong(song: Song){
        songList.add(song)
        notifyDataSetChanged()
    }
}