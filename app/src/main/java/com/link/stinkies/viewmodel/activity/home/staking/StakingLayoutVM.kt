package com.link.stinkies.viewmodel.activity.home.staking

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.model.web3.ChainlinkReward
import com.link.stinkies.model.web3.Web3Manager

class StakingLayoutVM : ViewModel() {

    var reward = Web3Manager.reward

    var chainlinkStats = CoinCapRepo.chainlink

    var loading = mutableStateOf(false)

    var error = mutableStateOf(false)

}