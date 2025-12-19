package com.nikhil.sensingassignment

import androidx.lifecycle.ViewModel

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class GraphViewModel:ViewModel()
{
    private val signal=MutableStateFlow<List<Int>>(emptyList())
    val signalstate=signal.asStateFlow()
    init {
        startnumber()
    }
    private fun startnumber()
    {
       viewModelScope.launch(Dispatchers.Default){
            while (true)
            {
                val newnum=Random.nextInt(0,101)
                signal.update{ currentlist ->
                    val newlist = currentlist.toMutableList()
                    newlist.add(newnum)
                    if (newlist.size > 300) {
                        newlist.removeAt(0)
                    }
                    newlist
                }
                delay(100)
            }
       }
    }
}