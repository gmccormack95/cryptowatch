package com.link.stinkies.layout.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.link.stinkies.ui.theme.background
import com.link.stinkies.viewmodel.activity.HomeActivityVM

@Composable
fun SettingsLayout(viewModel: HomeActivityVM) {
    Column(
        Modifier
            .fillMaxSize()
            .background(background)
    ) {
        Text(
            text = "Settings Screen"
        )
    }
}