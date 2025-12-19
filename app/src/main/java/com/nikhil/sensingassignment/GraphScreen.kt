package com.nikhil.sensingassignment

import android.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import java.nio.file.WatchEvent.Modifier

@Composable
fun GraphScreen(viewModel: GraphViewModel)
{
    val graphbeats by viewModel.signalstate.collectAsState()
    Canvas(
        modifier=Modifier
            .fillMaxSize()
            .background(Color.BLACK)
    )
}

