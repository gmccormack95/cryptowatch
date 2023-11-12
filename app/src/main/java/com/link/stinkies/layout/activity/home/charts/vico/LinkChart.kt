package com.link.stinkies.layout.activity.home.charts.vico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.link.stinkies.ui.theme.white
import com.link.stinkies.viewmodel.activity.HomeActivityVM
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.endAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberEndAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

@Composable
fun LinkChart(viewModel: HomeActivityVM) {
    val chainlinkHourly = viewModel.linkHourly.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Chart(
            chart = lineChart(
                axisValuesOverrider = AxisValuesOverrider.fixed(
                    minY = (chainlinkHourly.value?.low ?: 0.5f) - 0.5f,
                    maxY = (chainlinkHourly.value?.high ?: -0.5f) + 0.5f,
                ),
                lines = listOf(
                    lineSpec(
                        lineColor = white,
                        lineThickness = 3.dp,
                        lineBackgroundShader = verticalGradient(
                            arrayOf(
                                white.copy(0.5f),
                                white.copy(alpha = 0f)
                            ),
                        ),
                    ),
                ),
                spacing = 0.1.dp
            ),
            chartModelProducer = chainlinkHourly.value?.chartEntryProducer() ?:
            ChartEntryModelProducer(arrayListOf<ChartEntry>()),
            startAxis = rememberStartAxis(
                label = null,
                guideline = null,
                tick = null,
                axis = null
            ),
            bottomAxis = rememberBottomAxis(
                label = null,
                guideline = null,
                tick = null,
                axis = null
            ),
            marker = rememberMarker(),
            isZoomEnabled = false,
            runInitialAnimation = false,
            modifier = Modifier
                .height(300.dp)
        )
        Row(

        ) {

        }
    }
}