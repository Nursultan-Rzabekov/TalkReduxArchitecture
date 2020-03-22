package com.example.mvireduxapp.mvi.api

data class Movie(val title: String, val content: String) {
  override fun toString(): String {
    return "Movie(title=$title, content=${content.substring(0..20)}...)"
  }
}
