package com.link.stinkies.view.activity.thread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.link.stinkies.layout.activity.home.HomeActivityLayout
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.ui.theme.StinkiesTheme
import com.link.stinkies.viewmodel.activity.HomeActivityVM

class HomeActivity : ComponentActivity() {

    private lateinit var viewModel: HomeActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeActivityVM::class.java)
        viewModel.init(BizRepo)

        setContent {
            StinkiesTheme {
                HomeActivityLayout(viewModel)
            }
        }

    }
}