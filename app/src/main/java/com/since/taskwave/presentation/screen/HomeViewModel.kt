package com.since.taskwave.presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.since.taskwave.data.modal.database.Quotes
import com.since.taskwave.domain.repo.db.QuotesRepo
import com.since.taskwave.domain.repo.network.NetworkApiRepo
import com.since.taskwave.domain.repo.network.ObserveNetwork
import com.since.taskwave.domain.repo.worker.WorkerRepo
import com.since.taskwave.presentation.screen.state_action.QuotesAction
import com.since.taskwave.presentation.screen.state_action.QuotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quotesRepo: QuotesRepo,
    private val workerRepo: WorkerRepo,
    private val observeNetwork: ObserveNetwork
) : ViewModel() {
    var state by mutableStateOf(QuotesState())
        private set

    val networkObserve = observeNetwork
        .observeNetwork
        .stateIn(viewModelScope, SharingStarted.Eagerly,false)



    var quotesItem = quotesRepo
        .getAllData().stateIn(viewModelScope, SharingStarted.Eagerly,emptyList())

    init {
        viewModelScope.launch {
            workerRepo.oneTimeWorker()
        }
    }


    fun onAction(action: QuotesAction){
        when(action){
            is QuotesAction.Delete -> {
                deleteData(action.quotes)
            }

            QuotesAction.RepeatTimeRequest -> {
                viewModelScope.launch {
                    workerRepo.periodicTimeWorker()
                }
            }
        }
    }


    private fun deleteData(quotes: Quotes?){
        viewModelScope.launch {
            quotes?.let {
                quotesRepo.deleteData(it)
            }
        }
    }

}