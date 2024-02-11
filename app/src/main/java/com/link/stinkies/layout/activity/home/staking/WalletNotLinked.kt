package com.link.stinkies.layout.activity.home.staking

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.link.stinkies.R
import com.link.stinkies.model.web3.Web3Manager
import com.link.stinkies.ui.theme.linkBlue
import com.link.stinkies.viewmodel.activity.home.staking.StakingLayoutVM

@Composable
fun WalletNotLinked(viewModel: StakingLayoutVM) {
    var walletAddress by remember { mutableStateOf("") }
    val error = viewModel.error
    val loading = viewModel.loading

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.confused_pepe),
            contentDescription = "No No Linkers",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .wrapContentSize()
                .height(250.dp)
        )
        Text(
            text = "No wallet address linked.",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(bottom = 32.dp)
        )
        OutlinedTextField(
            value = walletAddress,
            onValueChange = { walletAddress = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, bottom = 16.dp),
            placeholder = {
                Text(
                    text = "wallet address...",
                    style = TextStyle(
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                )
            },
            shape = RoundedCornerShape(6.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = linkBlue
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        )
        Button(
            onClick = {
                loading.value = true
                error.value = false
                Web3Manager.getStakingRewards(
                    address = walletAddress,
                    onComplete = { reward ->
                        viewModel.reward.postValue(reward)
                    },
                    onError = { exception ->
                        loading.value = false
                        error.value = true
                    }
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            content = {
                if (loading.value) {
                    CircularProgressIndicator(
                        color = linkBlue,
                        strokeWidth = 3.dp,
                        modifier = Modifier
                            .size(20.dp)
                    )
                } else {
                    Text(
                        text = "Link",
                        style = TextStyle(
                            color = linkBlue,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
        )
        if (error.value) {
            Text(
                text = "There was an error linking wallet",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
        }
    }
}