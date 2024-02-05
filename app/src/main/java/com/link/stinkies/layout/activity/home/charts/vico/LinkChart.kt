package com.link.stinkies.layout.activity.home.charts.vico

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.link.stinkies.model.Constant
import com.link.stinkies.ui.theme.white
import com.link.stinkies.viewmodel.activity.charts.ChartLayoutVM
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.line.lineSpec
import com.patrykandpatrick.vico.compose.component.shape.shader.verticalGradient
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

enum class Interval(var label: String, var topLabel: String, var coinCapValue: String, var milliseconds: Long) {
    Day1(label = "1D", topLabel = "24 Hours",coinCapValue = "m1", milliseconds = Constant.DAY_MILLI),
    Week1(label = "7D", topLabel = "7 Days", coinCapValue = "m30", milliseconds = Constant.WEEK_MILLI),
    Month1(label = "1M", topLabel = "1 Month", coinCapValue = "h1", milliseconds = Constant.MONTH_MILLI),
    Year1(label = "1Y", topLabel = "1 Year", coinCapValue = "h12", milliseconds = Constant.YEAR_MILLI),
}

@Composable
fun LinkChart(viewModel: ChartLayoutVM) {
    val chartData = viewModel.chainlinkData.observeAsState()
    val difference = (viewModel.chainlinkData.value?.high ?: 0f) - (viewModel.chainlinkData.value?.low ?: 0f)
    val padding = difference / 5f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Chart(
            chart = lineChart(
                axisValuesOverrider = AxisValuesOverrider.fixed(
                    minY = (chartData.value?.low ?: padding) - padding,
                    maxY = (chartData.value?.high ?: -padding) + padding,
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
                spacing = 0.2.dp,
                decorations = listOf(rememberTopThresholdLine(chartData.value?.high), rememberBottomThresholdLine(chartData.value?.low)),
            ),
            chartModelProducer = chartData.value?.chartEntryModelProducer ?:
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
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Day(
                interval = Interval.Day1,
                viewModel = viewModel
            )
            Day(
                interval = Interval.Week1,
                viewModel = viewModel
            )
            Day(
                interval = Interval.Month1,
                viewModel = viewModel
            )
            Day(
                interval = Interval.Year1,
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun rememberTopThresholdLine(high: Float?): ThresholdLine {
    val line = shapeComponent(color = Color.Transparent)
    val label = textComponent(
        color = Color.White,
        typeface = Typeface.MONOSPACE,
        padding = dimensionsOf(
            end = 16.dp,
            bottom = 16.dp
        )
    )
    return remember(line, label, high) {
        ThresholdLine(
            thresholdValue = high ?: 0f,
            thresholdLabel = String.format("%.2f", high),
            lineComponent = line,
            labelComponent = label,
            labelHorizontalPosition = ThresholdLine.LabelHorizontalPosition.End,
            labelVerticalPosition = ThresholdLine.LabelVerticalPosition.Top
        )
    }
}

@Composable
private fun rememberBottomThresholdLine(low: Float?): ThresholdLine {
    val line = shapeComponent(color = Color.Transparent)
    val label = textComponent(
        color = Color.White,
        typeface = Typeface.MONOSPACE,
        padding = dimensionsOf(
            start = 16.dp,
            top = 16.dp
        )
    )
    return remember(line, label, low) {
        ThresholdLine(
            thresholdValue = low ?: 0f,
            thresholdLabel = String.format("%.2f", low),
            lineComponent = line,
            labelComponent = label,
            labelHorizontalPosition = ThresholdLine.LabelHorizontalPosition.Start,
            labelVerticalPosition = ThresholdLine.LabelVerticalPosition.Bottom
        )
    }
}

@Composable
private fun Day(interval: Interval, viewModel: ChartLayoutVM) {
    val selectedInterval = viewModel.interval.observeAsState()

    Box(
        modifier = Modifier
            .padding(12.dp)
            .background(
                color = if (selectedInterval.value == interval)
                    Color.White.copy(alpha = 0.4f)
                else
                    Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                viewModel.interval.value = interval
                viewModel.refreshCoinCap()
            }
    ) {
        Text(
            text = interval.label,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp)
        )
    }
}