package com.link.stinkies.model.web3

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.util.Log.d
import androidx.lifecycle.MutableLiveData
import com.link.stinkies.StinkiesApplication
import com.link.stinkies.model.Key
import com.link.stinkies.model.StartUp
import com.link.stinkies.model.coincap.CoinCapRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import kotlin.math.pow

object Web3Manager {

    val reward: MutableLiveData<ChainlinkReward> = MutableLiveData()

    private const val etheriumNode = "https://mainnet.infura.io/v3/724494f87828475988f55f1301dc2dfe"
    private const val contractAddress = "0x996913c8c08472f584ab8834e925b06D0eb1D813"

    private var sharedPreferences: SharedPreferences? = null

    private const val REFRESH_TIME: Long = 60 * 60 * 1000

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(StinkiesApplication.APP_NAME, Context.MODE_PRIVATE)

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                getStakingRewards(null, {}, {})
                mainHandler.postDelayed(this, REFRESH_TIME)
            }
        })
    }

    fun getStakingRewards(address: String?, onComplete: (ChainlinkReward) -> Unit, onError: (Exception) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            address?.run {
                sharedPreferences?.edit()?.putString(Key.stakingAddress, address)?.apply()
            }

            val walletAddress = address ?: run {
                sharedPreferences?.getString(Key.stakingAddress, null)
            } ?: return@launch

            val web3j = Web3j.build(HttpService(etheriumNode))
            val transactionManager = ReadonlyTransactionManager(web3j, contractAddress)
            val contract: LinkStaking = LinkStaking.load(
                contractAddress,
                web3j,
                transactionManager,
                DefaultGasProvider()
            )

            val chainlinkReward = ChainlinkReward()

            try {
                val rewardResult = contract.getReward(walletAddress).sendAsync().get()
                chainlinkReward.claimable = rewardResult.toFloat() * 10.0f.pow(-18)
                d("Web3Manager", "claimable: ${chainlinkReward.claimable}")

                val lockedRewardResult = contract.calculateLatestStakerReward(walletAddress).sendAsync().get()
                chainlinkReward.locked = lockedRewardResult.component2().toFloat() * 10.0f.pow(-18)
                d("Web3Manager", "locked: ${chainlinkReward.locked}")
            } catch (e: Exception) {
                d("Web3Manager", "exception: ${e.message}")
                onError(e)
            }

            reward.postValue(chainlinkReward)
            onComplete(chainlinkReward)
        }
    }

    fun clearWallet() {
        sharedPreferences?.edit()?.remove(Key.stakingAddress)?.apply()
        reward.value = null
    }

}