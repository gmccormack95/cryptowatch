@file:OptIn(ExperimentalMaterialApi::class)

package com.link.stinkies.layout.activity.home.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.link.stinkies.layout.activity.home.charts.performance.AssetPerformanceCard
import com.link.stinkies.layout.activity.home.charts.vico.LinkChart
import com.link.stinkies.ui.theme.background
import com.link.stinkies.ui.theme.financeGreen
import com.link.stinkies.ui.theme.financeRed
import com.link.stinkies.ui.theme.linkBlue
import com.link.stinkies.viewmodel.activity.charts.ChartLayoutVM

@Composable
fun ChartLayout(viewModel: ChartLayoutVM) {
    val isRefreshing = viewModel.loading.observeAsState()
    val pullRefreshState = rememberPullRefreshState(
        isRefreshing.value == true,
        {
            viewModel.refreshCoinCap()
        }
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .fillMaxHeight()
                .background(linkBlue)
        ) {
            Column(
                modifier = Modifier
            ) {
                Header(viewModel = viewModel)
                LinkChart(viewModel = viewModel)
                LinkStats(viewModel = viewModel)
                Top10(viewModel = viewModel)
            }
        }
        PullRefreshIndicator(
            isRefreshing.value == true,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun Header(viewModel: ChartLayoutVM) {
    val chartData = viewModel.chainlinkData.observeAsState()
    val chainlink = viewModel.chainlink.observeAsState()
    val interval = viewModel.interval.observeAsState()

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = interval.value?.topLabel ?: "24 Hours",
            color = Color.White.copy(alpha = .4f),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            modifier = Modifier
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowDropDown,
                contentDescription = "increase-decrease icon",
                tint = if(chartData.value?.increase == true) financeGreen else financeRed,
                modifier = Modifier
                    .rotate(if(chartData.value?.increase == true) 180f else 0f)
            )
            Text(
                text = "${String.format("%.4f", chartData.value?.priceChange)}%",
                color = if(chartData.value?.increase == true) financeGreen else financeRed,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.W500,
                fontSize = 24.sp,
                modifier = Modifier
            )
        }
        Text(
            text = String.format("%.2f", chainlink.value?.priceUsd),
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 48.sp,
            modifier = Modifier
                .padding(top = 32.dp)
        )
        Text(
            text = "USD",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
        )
    }
}

@Composable
private fun LinkStats(viewModel: ChartLayoutVM) {
    val chainlink = viewModel.chainlink.observeAsState()
    val volumeMarketCap = (chainlink.value?.volumeUsd24Hr ?: 1f)/(chainlink.value?.maxSupply ?: 1f)

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 32.dp
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(bottom = 32.dp)
        ) {
            Column (
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = "Market Cap",
                    color = Color.White.copy(alpha = .4f),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    modifier = Modifier
                )
                Text(
                    text = "$${"%,d".format(chainlink.value?.marketCapUsd?.toInt())}",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp,
                    modifier = Modifier
                )
            }
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Column (
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Circulating Supply",
                    color = Color.White.copy(alpha = .4f),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    modifier = Modifier
                )
                Text(
                    text = "${"%,d".format(chainlink.value?.supply?.toInt())} LINK",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp,
                    modifier = Modifier
                )
            }
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(bottom = 32.dp)
        ){
            Column(
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = "Volume (24h)",
                    color = Color.White.copy(alpha = .4f),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    modifier = Modifier
                )
                Text(
                    text = "$${"%,d".format(chainlink.value?.volumeUsd24Hr?.toInt())}",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp,
                    modifier = Modifier
                )
            }
            Spacer(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Column (
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Max Supply",
                    color = Color.White.copy(alpha = .4f),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    modifier = Modifier
                )
                Text(
                    text = "${"%,d".format(chainlink.value?.maxSupply?.toInt())} LINK",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp,
                    modifier = Modifier
                )
            }
        }
        Column {
            Text(
                text = "Volume/Market cap (24h)",
                color = Color.White.copy(alpha = .4f),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = Modifier
            )
            Text(
                text = "${volumeMarketCap * 100}%",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.W500,
                fontSize = 20.sp,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun Top10(viewModel: ChartLayoutVM) {
    val top10 = viewModel.top10.observeAsState()

    Column(
        modifier = Modifier
            .background(background)
            .padding(16.dp)
    ){
        Text(
            text = "Top Market Cap Assets",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            modifier = Modifier
                .padding(bottom = 16.dp)
        )

        top10.value?.data?.forEach {
            AssetPerformanceCard(it)
        }
    }
}

