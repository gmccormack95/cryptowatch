@file:OptIn(ExperimentalGlideComposeApi::class)

package com.link.stinkies.layout.activity.home.charts.performance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.link.stinkies.model.coincap.TokenHistory
import com.link.stinkies.model.coincap.TokenStats
import com.link.stinkies.ui.utils.BezierCurve
import com.link.stinkies.ui.utils.BezierCurveStyle
import com.link.stinkies.ui.theme.financeGreen
import com.link.stinkies.ui.theme.financeRed

@Composable
fun AssetPerformanceCard(
    token: TokenStats
) {
    val chartData = token.chartData.observeAsState()
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.4f))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            GlideImage(
                model = token.iconUrl ?: "https://coinicons-api.vercel.app/api/icon/btc",
                contentDescription = "Token icon",
                contentScale = ContentScale.Fit,
                transition = CrossFade,
                modifier = Modifier
                    .height(48.dp)
                    .clip(CircleShape)
            )

            TickerName(token.name ?: "", token.symbol ?: "")

            PerformanceChart(chartData.value)

            ValueView(token.priceUsd ?: 0F)
        }
    }
}

@Composable
fun ValueView(currentValue: Float) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .padding(start = 10.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "$${String.format("%.2f", currentValue)}",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun PerformanceChart(chartData: TokenHistory?) {
    val list = chartData?.getPrices() ?: return

    Row(
        modifier = Modifier
            .height(40.dp)
            .width(70.dp)
    ) {
        val max = list.max()
        val min = list.min()
        val lineColor = if (list.last() > list.first()) financeGreen else financeRed

        BezierCurve(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            points = list,
            minPoint = min,
            maxPoint = max,
            style = BezierCurveStyle.CurveStroke(
                brush = Brush.linearGradient(listOf(lineColor, lineColor)),
                stroke = Stroke(width = 6f)
            ),
        )
    }
}

@Composable
//@Preview
private fun TickerName(name: String = "Apple Inc.", tickerName: String = "AAPL") {
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 5.dp)
            .width(80.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Text(text = tickerName, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
}
