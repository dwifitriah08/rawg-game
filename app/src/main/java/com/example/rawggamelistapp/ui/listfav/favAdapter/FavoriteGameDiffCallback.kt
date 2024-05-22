package com.example.rawggamelistapp.ui.listfav.favAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.rawggamelistapp.data.model.FavoriteGame

class FavoriteGameDiffCallback : DiffUtil.ItemCallback<FavoriteGame>() {
    override fun areItemsTheSame(oldItem: FavoriteGame, newItem: FavoriteGame): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteGame, newItem: FavoriteGame): Boolean {
        return oldItem == newItem
    }
}
