package com.link.stinkies.model.web3

import android.util.Log.d
import com.link.stinkies.LinkStaking
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService
import org.web3j.tx.ReadonlyTransactionManager
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import java.math.BigInteger
import kotlin.math.pow

object Web3Manager {

    fun getStakingRewards() {
        CoroutineScope(Dispatchers.IO).launch {
            val web3j = Web3j.build(HttpService("https://mainnet.infura.io/v3/724494f87828475988f55f1301dc2dfe"))
            val transactionManager = ReadonlyTransactionManager(web3j, "0x996913c8c08472f584ab8834e925b06D0eb1D813")
            val contract: LinkStaking = LinkStaking.load(
                "0x996913c8c08472f584ab8834e925b06D0eb1D813",
                web3j,
                transactionManager,
                DefaultGasProvider()
            )

            val result = contract.getReward("0x6Fc86cbC3cAe7c3f03Dd279b1D9bB5CF3675bDa0").sendAsync().get()
            val chainlinkRewards: Float = result.toFloat() * 10.0f.pow(-18)
            d("Web3Manager", "getReward(): $chainlinkRewards")
        }
    }

}