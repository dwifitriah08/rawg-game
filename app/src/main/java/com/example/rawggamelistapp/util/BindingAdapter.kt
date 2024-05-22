package com.example.rawggamelistapp.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.rawggamelistapp.data.model.Genre

@BindingAdapter("genresList")
fun setGenresList(textView: TextView, genres: List<Genre>?) {
    textView.text = genres?.joinToString(separator = ", ")
}

