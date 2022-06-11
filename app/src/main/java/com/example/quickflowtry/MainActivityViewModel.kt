package com.example.quickflowtry

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel : ViewModel() {
    private var iterator: Int = 1
    private val flowList: MutableList<Int> = mutableStateListOf()
    private val _uiState = MutableStateFlow<List<Int>>(emptyList())

    val uiState: StateFlow<List<Int>>
        get() = _uiState

    fun incrementByOne() {
        flowList.add(iterator++)
        _uiState.value = flowList
    }
}
