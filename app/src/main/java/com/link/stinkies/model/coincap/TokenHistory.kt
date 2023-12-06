package com.link.stinkies.model.coincap

import com.patrykandpatrick.vico.core.entry.ChartEntry
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf

class TokenHistory {

    var data: ArrayList<PricePoint> = arrayListOf()

    var timestamp: Long? = null

    var currentPrice: Float? = null
        get() {
            return data.last().priceUsd
        }

    var low: Float? = null
        get() {
            var low = high ?: 0f
            data.forEach { pricePoint ->
                pricePoint.priceUsd?.let {
                    if(low > it) low = it
                }
            }

            return low
        }

    var high: Float? = null
        get() {
            var high = 0f
            data.forEach { pricePoint ->
                pricePoint.priceUsd?.let {
                    if(high < it) high = it
                }
            }

            return high
        }

    var priceChange: Float? = null
        get() {
            val difference: Float = (data.last().priceUsd ?: 0f) - (data.first().priceUsd ?: 0f)
            val average: Float? = data.last().priceUsd?.plus(data.first().priceUsd ?: 0f)?.div(2)

            average?.let {
                return (difference/average) * 100
            }

            return null
        }

    var increase: Boolean? = null
        get() {
            priceChange?.let {
                return it >= 0
            }

            return null
        }

    val chartEntryModelProducer = ChartEntryModelProducer()



    fun updateModel() {
        val prices = arrayListOf<ChartEntry>()

        data.forEachIndexed { index, pricePoint ->
            pricePoint.priceUsd?.let {
                prices.add(entryOf(index, it))
            }
        }

        chartEntryModelProducer.setEntries(prices)
    }

    fun getPrices(): List<Float> {
        val prices = arrayListOf<Float>()

        data.forEach { price ->
            price.priceUsd?.let { prices.add(it) }
        }

        return prices
    }

}