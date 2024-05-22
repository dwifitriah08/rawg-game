package com.example.rawggamelistapp.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.rawggamelistapp.R
import com.example.rawggamelistapp.data.GameRepository
import com.example.rawggamelistapp.data.database.AppDatabase
import com.example.rawggamelistapp.data.database.FavoriteGameDao
import com.example.rawggamelistapp.data.model.GameDetail
import com.example.rawggamelistapp.data.remote.RawgApiService
import com.example.rawggamelistapp.network.RetrofitClient
import com.example.rawggamelistapp.databinding.DetailGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast

class GameDetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailGameBinding
    private lateinit var repository: GameRepository
    private var isFavorite: Boolean = false
    private lateinit var gameDet: GameDetail
    private lateinit var favoriteGameDao: FavoriteGameDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app-database").build()
        favoriteGameDao = db.favoriteGameDao()

        val apiService = RetrofitClient.instance.create(RawgApiService::class.java)
        repository = GameRepository(apiService, favoriteGameDao)

        val title = intent.getStringExtra(EXTRA_TITLE)
        val image = intent.getStringExtra(EXTRA_IMAGE)
        val release = intent.getStringExtra(EXTRA_RELEASE)
        val genre = intent.getStringExtra(EXTRA_GENRE)
        val gameId = intent.getIntExtra(EXTRA_GAME_ID, -1)

        // Set data to views
        binding.titleTextView.text = title
        binding.realeseValue.text = release
        binding.genreValue.text = genre
        binding.progressBar.visibility = android.view.View.VISIBLE
        Glide.with(binding.root.context)
            .load(image)
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

        if (gameId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.favoriteIcon.visibility = View.INVISIBLE
                    }

                    val gameDetail = repository.getGameDetail(gameId)

                    withContext(Dispatchers.Main) {
                        gameDet = gameDetail
                        binding.favoriteIcon.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.descValue.text = gameDetail.description
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {

        }

        binding.favoriteIcon.setOnClickListener {
            isFavorite = !isFavorite
            if (isFavorite) {
                binding.favoriteIcon.setImageResource(R.drawable.ic_favorite_red)
                CoroutineScope(Dispatchers.IO).launch {
                    val countBefore = repository.getAllFavoriteGames().size
                    repository.saveToDBFavorites(gameDet, true)
                    val countAfter = repository.getAllFavoriteGames().size
                    if(countBefore===countAfter){
                        runOnUiThread {
                            Toast.makeText(this@GameDetailActivity, "This game already on favorite", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        runOnUiThread {
                            Toast.makeText(this@GameDetailActivity, "Success add game to favorite", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                binding.favoriteIcon.setImageResource(R.drawable.ic_favorite)
            }
        }

    }


    companion object {
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_RELEASE = "extra_release"
        const val EXTRA_GENRE = "extra_genre"
        const val EXTRA_GAME_ID = "extra_game_id"
    }
}

