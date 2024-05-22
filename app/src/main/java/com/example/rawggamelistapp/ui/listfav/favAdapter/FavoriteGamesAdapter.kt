package com.example.rawggamelistapp.ui.listfav.favAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rawggamelistapp.data.model.FavoriteGame
import com.example.rawggamelistapp.databinding.ItemFavoriteGameBinding

class FavoriteGamesAdapter(private val deleteListener: FavoriteGameDeleteListener) :
    ListAdapter<FavoriteGame, FavoriteGamesAdapter.FavoriteGameViewHolder>(FavoriteGameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteGameViewHolder {
        val binding = ItemFavoriteGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteGameViewHolder, position: Int) {
        val favoriteGame = getItem(position)
        holder.bind(favoriteGame)
    }

    inner class FavoriteGameViewHolder(private val binding: ItemFavoriteGameBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.favoriteIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val favoriteGame = getItem(position)
                    deleteListener.onDeleteClicked(favoriteGame)
                }
            }
        }

        fun bind(favoriteGame: FavoriteGame) {
            binding.game = favoriteGame
            binding.realeseValue.text = favoriteGame.releaseDate
            binding.genreValue.text = favoriteGame.genre
            Glide.with(binding.root.context)
                .load(favoriteGame.imageUrl)
                .into(binding.imgGame)
            binding.executePendingBindings()
        }
    }

}

interface FavoriteGameDeleteListener {
    fun onDeleteClicked(favoriteGame: FavoriteGame)
}

