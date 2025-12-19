package com.nikhil.sensingassignment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GraphViewModel:ViewModel()
{
    private val signal=MutableStateFlow<List<Int>>(emptyList())
    val signalstate=signal.asStateFlow()
    init {
        startnumber()
    }
    private fun startnumber()
    {

    }
}