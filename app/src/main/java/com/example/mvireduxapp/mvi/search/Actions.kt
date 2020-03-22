package com.example.mvireduxapp.mvi.search

import com.example.mvireduxapp.mvi.api.Movie
import com.example.mvireduxapp.mvi.redux.Action


sealed class UiAction : Action {
  data class SearchAction(val query: String) : UiAction()
  data class LoadSuggestionsAction(val query: String) : UiAction()
}

sealed class InternalAction : Action {
  object SearchLoadingAction : InternalAction()
  data class SearchSuccessAction(val data: Movie) : InternalAction()
  data class SearchFailureAction(val error: Throwable) : InternalAction()
  data class SuggestionsLoadedAction(val suggestions: List<String>) : InternalAction()
}
