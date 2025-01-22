package com.link.stinkies.model.web3

class ChainlinkReward {

    var claimable: Float? = null

    var locked: Float? = null

    val total: Float
        get() = (claimable ?: 0f) + (locked ?: 0f)

}