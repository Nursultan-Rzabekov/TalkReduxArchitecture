package com.example.mvireduxapp.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvireduxapp.mvi.redux.Action
import com.example.mvireduxapp.mvi.redux.MviView
import com.example.mvireduxapp.mvi.search.SearchStore
import com.example.mvireduxapp.mvi.search.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
class SearchBinder : ViewModel() {

  private val store = SearchStore(Dispatchers.IO)

  init {
    store.wire(scope = viewModelScope)
  }

  fun bind(view: MviView<Action, UiState>, uiScope: CoroutineScope) {
    store.bind(view, uiScope)
  }
}
