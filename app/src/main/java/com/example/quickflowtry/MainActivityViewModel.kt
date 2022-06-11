package com.example.quickflowtry

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.parcelize.Parcelize

@Parcelize
data class UiStateModel(val data: List<Int> = mutableStateListOf()) : Parcelable

class MainActivityViewModel : ViewModel() {
    private var iterator: Int = 1
    private val flowList: MutableList<Int> = mutableListOf()
    private val _uiState = MutableStateFlow(UiStateModel())

    val uiState: StateFlow<UiStateModel>
        get() = _uiState

    fun incrementByOne() {
        flowList.add(iterator++)
        _uiState.getAndUpdate { _uiState.value.copy(data = flowList) }
    }
}
