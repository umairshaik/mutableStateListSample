package com.example.quickflowtry

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiStateModel(val data: List<Int> = mutableStateListOf()) : Parcelable

class MainActivityViewModel : ViewModel() {
    private var iterator: Int = 1
    private val flowList: MutableList<Int> = mutableListOf()
    private val holder = MutableSharedFlow<List<Int>>()
    private val _uiState = MutableStateFlow(UiStateModel())

    val uiState: StateFlow<UiStateModel>
        get() = _uiState

    fun incrementByOne() {
        flowList.add(iterator++)
        viewModelScope.launch {
            holder.emit(flowList)
        }
    }

    init {
        viewModelScope.launch {
            holder.collectLatest { data ->
                _uiState.getAndUpdate { _uiState.value.copy(data = data) }
            }
        }
    }
}
