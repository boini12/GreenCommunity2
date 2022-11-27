package org.wit.greencommunity.main

import android.app.Application
import com.github.ajalt.timberkt.Timber
import org.wit.greencommunity.models.AdMemStore
import org.wit.greencommunity.models.AdModel
import timber.log.Timber.i


class MainApp : Application() {

    val ads = AdMemStore()

    override fun onCreate(){
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("GreenCommunity started")

        ads.create(AdModel(1, "Fresh Tomatoes"))
        ads.create(AdModel(2, "Green Peas"))
        ads.create(AdModel(1, "Fresh Tomatoes"))
        ads.create(AdModel(2, "Green Peas"))
        ads.create(AdModel(1, "Fresh Tomatoes"))
        ads.create(AdModel(2, "Green Peas"))
        ads.create(AdModel(1, "Fresh Tomatoes"))
        ads.create(AdModel(2, "Green Peas"))

    }
}