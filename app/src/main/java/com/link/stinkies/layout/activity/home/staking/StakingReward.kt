package com.link.stinkies.layout.activity.home.staking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.link.stinkies.viewmodel.activity.home.staking.StakingLayoutVM

@Composable
fun StakingReward(viewModel: StakingLayoutVM) {
    val reward = viewModel.reward.observeAsState()
    val chainlinkStats = viewModel.chainlinkStats.observeAsState()

    Column(
        modifier = Modifier
            .padding(top = 50.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ){
        Text(
            text = "Total Value",
            color = Color.White.copy(alpha = 0.6f),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp,
            modifier = Modifier
        )
        Text(
            text = "$${String.format(
                "%.2f", reward.value?.total?.times((chainlinkStats.value?.priceUsd ?: 1f))
            )}",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 48.sp,
            modifier = Modifier
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Column {
                Text(
                    text = "Link",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    modifier = Modifier
                )
                Text(
                    text = "${String.format("%.3f", reward.value?.total)}",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                )
            }
            Column (
                horizontalAlignment = Alignment.End
            ){
                Text(
                    text = "APY",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    modifier = Modifier
                )
                Text(
                    text = "4.32%",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Column {
                Text(
                    text = "Claimable Link",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    modifier = Modifier
                )
                Text(
                    text = "${String.format("%.3f", reward.value?.claimable)}",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                )
            }
            Column (
                horizontalAlignment = Alignment.End
            ){
                Text(
                    text = "Locked Link",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    modifier = Modifier
                )
                Text(
                    text = "${String.format("%.3f", reward.value?.locked)}",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    modifier = Modifier
                )
            }
        }
        RampUp(
            modifier = Modifier
                .padding(top = 64.dp)
        )
    }
}