package com.example.rawggamelistapp.ui.listfav

import android.os.Bundle
import android.view.View.GONE
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import com.example.rawggamelistapp.R

class FavoriteGamesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_game)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val favIcon: ImageView = findViewById(R.id.favIcon)
        favIcon.visibility = GONE
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Favorite Game")


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FavoriteGamesFragment.newInstance())
                .commit()
        }
    }
}
