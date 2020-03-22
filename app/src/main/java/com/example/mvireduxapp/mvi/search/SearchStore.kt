package com.example.mvireduxapp.mvi.search

import com.example.mvireduxapp.mvi.api.Api
import com.example.mvireduxapp.mvi.api.ProductionApi
import com.example.mvireduxapp.mvi.redux.Action
import com.example.mvireduxapp.mvi.redux.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


private val api: Api = ProductionApi()

@FlowPreview
@ExperimentalCoroutinesApi
class SearchStore(dispatcher: CoroutineDispatcher) : Store<Action, UiState>(
  SearchReducer(),
  listOf(
    SearchMiddleware(api, dispatcher),
    SuggestionsMiddleware(api, dispatcher)
  ),
  UiState()
)
