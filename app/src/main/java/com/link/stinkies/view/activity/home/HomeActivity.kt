package com.link.stinkies.view.activity.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.util.Log.e
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.coinbase.android.nativesdk.CoinbaseWalletSDK
import com.coinbase.android.nativesdk.message.request.Web3JsonRPC
import com.coinbase.android.nativesdk.message.response.ActionResult
import com.link.stinkies.layout.activity.home.HomeActivityLayout
import com.link.stinkies.layout.activity.home.bottomsheet.BottomSheet
import com.link.stinkies.model.ImageDownloadManager
import com.link.stinkies.model.StartUp
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.ui.theme.StinkiesTheme
import com.link.stinkies.viewmodel.activity.HomeActivityVM

class HomeActivity : ComponentActivity() {

    private lateinit var viewModel: HomeActivityVM
    //private lateinit var launcher: ActivityResultLauncher<Intent>

    private fun processIntent(): (Intent) -> Unit = { intent: Intent ->
        //launcher.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StartUp.init(this, BizRepo, CoinCapRepo)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                StartUp.loading.value ?: true
            }
        }

        viewModel = ViewModelProvider(this).get(HomeActivityVM::class.java)
        viewModel.init(BizRepo, CoinCapRepo)

        setContent {
            StinkiesTheme (
                dynamicColor = false
            ) {
                if (viewModel.bottomsheetVM.showSheet.value) {
                    BottomSheet(
                        viewModel = viewModel,
                        onDownload = { post ->
                            ImageDownloadManager.get(this, post)
                        },
                        onDismiss = {
                            viewModel.bottomsheetVM.showSheet.value = false
                        }
                    )
                }

                HomeActivityLayout(viewModel)
            }
        }

    }
}