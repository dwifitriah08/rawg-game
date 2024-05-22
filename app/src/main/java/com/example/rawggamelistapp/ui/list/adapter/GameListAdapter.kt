package com.example.rawggamelistapp.ui.list.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.rawggamelistapp.R
import com.example.rawggamelistapp.data.model.Game
import com.example.rawggamelistapp.databinding.ListItemGameBinding

class GameListAdapter(
    private var games: List<Game>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ListItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int = games.size

    fun submitList(newGames: List<Game>) {
        games = newGames
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(game: Game)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    inner class GameViewHolder(private val binding: ListItemGameBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(game: Game) {
            binding.titleTextView.text = game.name
            binding.genreValue.text = game.genres.joinToString(", ") { it.name }
            binding.realeseValue.text = game.released

            binding.progressBar.visibility = android.view.View.VISIBLE
            Glide.with(binding.root.context)
                .load(game.background_image)
                .override(Target.SIZE_ORIGINAL)
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        binding.imgGame.setImageDrawable(resource)
                        binding.progressBar.visibility = android.view.View.GONE
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        binding.progressBar.visibility = android.view.View.GONE
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        binding.progressBar.visibility = android.view.View.GONE
                    }
                })
        }

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val game = games[position]
                    listener.onItemClick(game)
                }
            }
        }
    }
}
