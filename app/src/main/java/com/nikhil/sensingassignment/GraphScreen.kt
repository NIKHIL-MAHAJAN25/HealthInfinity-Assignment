package com.nikhil.sensingassignment

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun GraphScreen(viewModel: GraphViewModel)
{
    val graphbeats by viewModel.signalstate.collectAsState()
    Canvas(
        modifier=Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
    {
        val radius = graphbeats.size.toFloat()

        drawCircle(
            color = Color.Red,
            radius = radius + 10f,
            center = center
        )
    }
}

