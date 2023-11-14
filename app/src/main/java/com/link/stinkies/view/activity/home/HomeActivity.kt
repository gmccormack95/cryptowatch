package com.link.stinkies.view.activity.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.link.stinkies.layout.activity.home.HomeActivityLayout
import com.link.stinkies.model.StartUp
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.ui.theme.StinkiesTheme
import com.link.stinkies.viewmodel.activity.HomeActivityVM

class HomeActivity : ComponentActivity() {

    private lateinit var viewModel: HomeActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                StartUp.loading.value ?: true
            }
        }

        viewModel = ViewModelProvider(this).get(HomeActivityVM::class.java)
        viewModel.init(BizRepo, CoinCapRepo)

        setContent {
            StinkiesTheme {
                HomeActivityLayout(viewModel)
            }
        }

        BizRepo.init(this)
        CoinCapRepo.init(this)
    }
}