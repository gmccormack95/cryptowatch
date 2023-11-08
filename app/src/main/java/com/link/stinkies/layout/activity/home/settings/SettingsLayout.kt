package com.link.stinkies.layout.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.link.stinkies.viewmodel.activity.HomeActivityVM

@Composable
fun SettingsLayout(viewModel: HomeActivityVM) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Settings Screen"
        )
    }
}