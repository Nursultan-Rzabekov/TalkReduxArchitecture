package com.example.mvireduxapp.mvi.api

import kotlinx.coroutines.flow.Flow

interface Api {
  fun search(query: String): Flow<Movie>
  fun suggestions(query: String): Flow<List<String>>
}
