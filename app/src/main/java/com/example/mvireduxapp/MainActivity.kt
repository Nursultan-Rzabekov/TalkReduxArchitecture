package com.example.mvireduxapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvireduxapp.flow.clicks
import com.example.mvireduxapp.flow.textChanges
import com.example.mvireduxapp.mvi.redux.Action
import com.example.mvireduxapp.mvi.redux.MviView
import com.example.mvireduxapp.mvi.search.UiAction
import com.example.mvireduxapp.mvi.search.UiState
import com.example.mvireduxapp.search.SearchBinder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), MviView<Action, UiState> {

    private val suggestionPicks = ConflatedBroadcastChannel<String>()

    private val recyclerAdapter = ItemAdapter {
        suggestionPicks.offer(it)
    }

    private val presenter: SearchBinder by viewModels()

    private val _actions by lazy {
        val clicks = submitBtn.clicks().map { UiAction.SearchAction(searchView.text.toString()) }
        val suggestions = suggestionPicks.asFlow().map { UiAction.SearchAction(it) }
        val textChanges = searchView.textChanges().map { UiAction.LoadSuggestionsAction(it.toString()) }

        listOf(clicks, suggestions, textChanges).asFlow().flattenMerge(3)
    }

    override val actions
        get() = _actions as Flow<Action>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        presenter.bind(this, lifecycleScope)
    }

    override fun render(state: UiState) {
        submitBtn.isEnabled = !state.loading
        progressView.visibility = if (state.loading) View.VISIBLE else View.GONE

        state.data?.let {
            titleView.text = it.title
            contentView.text = it.content
        } ?: run {
            titleView.text = null
            contentView.text = null
        }

        recyclerAdapter.replaceWith(state.suggestions ?: emptyList())

        state.error?.let { toast(it.localizedMessage) }
    }
}

