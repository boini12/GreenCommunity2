package org.wit.greencommunity.models

import timber.log.Timber.i

class AdMemStore: AdStore {
    var lastId = 0L


    var ads = ArrayList<AdModel>()

    override fun findAll(): List<AdModel> {
        return ads
    }

    override fun create(ad: AdModel) {
        ads.add(ad)
        logAll()
    }

    override fun update(ad: AdModel) {
        var foundAd: AdModel? = ads.find { p -> p.id  == ad.id}
        if(foundAd != null){
            foundAd.title = ad.title
            foundAd.description = ad.description
            foundAd.isFree = ad.isFree
            foundAd.price = ad.price
            logAll()
        }
    }

    fun logAll(){
        ads.forEach{ i("${it}")}
    }

    internal fun getId(): Long {
        return lastId++
    }

}