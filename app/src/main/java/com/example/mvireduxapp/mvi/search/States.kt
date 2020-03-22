package com.example.mvireduxapp.mvi.search

import com.example.mvireduxapp.mvi.api.Movie
import com.example.mvireduxapp.mvi.redux.State


data class UiState(
  val loading: Boolean = false,
  val data: Movie? = null,
  val error: Throwable? = null,
  val suggestions: List<String>? = null
) : State
