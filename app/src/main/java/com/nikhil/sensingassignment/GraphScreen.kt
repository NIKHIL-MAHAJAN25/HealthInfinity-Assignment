package com.nikhil.sensingassignment

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun GraphScreen(viewModel: GraphViewModel) {
    val graphbeats by viewModel.signalstate.collectAsState()
    val issmooth by viewModel._smoothbutton.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ){

        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
                .padding(16.dp)
        ) {
            Text(text = "Health Infinity Monitor")
            Text(text = "Points: ${graphbeats.size}")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Smooth Mode: ")
                Switch(
                    checked = issmooth,
                    onCheckedChange = { viewModel.togglesmooth() }
                )
            }

        }


        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color.Black)
        ) {
            val path = Path()
            val maxPoints = 300f
            val xStep = size.width / maxPoints
            val pointsToDraw = if (issmooth) {

                viewModel.smoothlist(graphbeats)
            } else {
                graphbeats
            }


            val linecolor = if (issmooth) Color.Cyan else Color.Green
            if (pointsToDraw.isNotEmpty()) {
                val firstY = size.height - (pointsToDraw[0] / 100f * size.height)
                path.moveTo(0f, firstY)
                pointsToDraw.forEachIndexed { index, value ->
                    if (index > 0) {
                        val x = index * xStep
                        val y = size.height - (value / 100f * size.height)
                        path.lineTo(x, y)
                    }
                }
                  drawPath(
                    path = path,
                    color = linecolor,
                    style = Stroke(width = 5f)
                )
            }
        }
    }
}

