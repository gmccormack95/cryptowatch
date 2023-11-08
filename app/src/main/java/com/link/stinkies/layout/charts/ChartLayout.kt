package com.link.stinkies.layout.charts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.link.stinkies.layout.catalog.CatalogLayout
import com.link.stinkies.viewmodel.activity.HomeActivityVM

@Composable
fun ChartLayout(viewModel: HomeActivityVM) {
    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Chart Screen"
        )
    }
}