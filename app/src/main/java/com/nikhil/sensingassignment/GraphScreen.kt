package com.nikhil.sensingassignment

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun GraphScreen(viewModel: GraphViewModel) {
    val graphbeats by viewModel.signalstate.collectAsState()
    val issmooth by viewModel._smoothbutton.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeDrawingPadding()
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape=RoundedCornerShape(16.dp),
            colors=CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E1E)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement=Arrangement.SpaceBetween,
                verticalAlignment=Alignment.CenterVertically
            ) {

                Column {
                    Text(
                        text="SENSOR MONITOR",
                        color=Color.White,
                        fontSize=16.sp,
                        fontWeight=FontWeight.Bold,
                        letterSpacing=1.sp
                    )
                    Text(
                        text="SIGNAL BUFFER: ${graphbeats.size}",
                        color=Color(0xFF00FF00),
                        fontSize=12.sp,
                        fontFamily=FontFamily.Monospace
                    )
                }


                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = if (issmooth) "SMOOTH" else "RAW",
                        color = if (issmooth) Color.Cyan else Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Switch(
                        checked = issmooth,
                        onCheckedChange = { viewModel.togglesmooth() },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor=Color.Cyan,
                            checkedTrackColor=Color(0xFF004D40),
                            uncheckedThumbColor=Color.Gray,
                            uncheckedTrackColor=Color.Black
                        )
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(16.dp)
                .background(
                    Color(0xFF222222),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                )
                .padding(8.dp)
                .border(
                    2.dp,
                    Color.Gray,
                    androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                )
                .clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                val path = Path()
                val maxpoints = 300f
                val xStep = size.width / maxpoints
                val pointstodraw = if (issmooth) {
                    viewModel.smoothlist(graphbeats)
                } else {
                    graphbeats
                }


                val linecolor = if (issmooth) Color.Cyan else Color.Green
                if (pointstodraw.isNotEmpty()) {
                    val firstY = size.height-(pointstodraw[0]/100f*size.height)
                    path.moveTo(0f, firstY)
                    pointstodraw.forEachIndexed { index, value ->
                        if (index > 0) {
                            val x=index*xStep
                            val y=size.height-(value/100f*size.height)
                            path.lineTo(x,y)
                        }
                    }
                    drawPath(
                        path=path,
                        color=linecolor,
                        style=Stroke(width = 5f)
                    )
                }
            }
        }
    }
}

