package com.example.rawggamelistapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.room.Room
import com.example.rawggamelistapp.data.GameRepository
import com.example.rawggamelistapp.data.database.AppDatabase
import com.example.rawggamelistapp.data.remote.RawgApiService
import com.example.rawggamelistapp.network.RetrofitClient
import com.example.rawggamelistapp.ui.listfav.FavoriteGamesActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("RAWG GAMES")

        val fav = findViewById<ImageView>(R.id.favIcon)
        val db = AppDatabase.getInstance(this)
        val favoriteGameDao = db.favoriteGameDao()
        val apiService = RetrofitClient.instance.create(RawgApiService::class.java)
        val repository = GameRepository(apiService, favoriteGameDao)

        fav.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                if (repository.getAllFavoriteGames().isEmpty()) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "No favorite data", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    runOnUiThread {
                        val intent = Intent(this@MainActivity, FavoriteGamesActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GameListFragment.newInstance())
                .commitNow()
        }
    }


}
