package com.link.stinkies.view.activity.intro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.link.stinkies.R
import com.link.stinkies.layout.activity.home.HomeActivityLayout
import com.link.stinkies.layout.activity.intro.IntroActivityLayout
import com.link.stinkies.model.StartUp
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.ui.theme.StinkiesTheme
import com.link.stinkies.view.activity.home.HomeActivity
import com.link.stinkies.viewmodel.activity.HomeActivityVM
import com.link.stinkies.viewmodel.activity.intro.IntroActivityVM

class IntroActivity : ComponentActivity() {

    private lateinit var viewModel: IntroActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(IntroActivityVM::class.java)
        viewModel.init(this, BizRepo, CoinCapRepo)

        setContent {
            StinkiesTheme {
                IntroActivityLayout(ContextCompat.getDrawable(this, R.drawable.splash_sequence))
            }
        }

        StartUp.loading.observe(this) { loading ->
            if (loading == false) {
                startActivity(Intent(this, HomeActivity::class.java))
            }
        }
    }
}