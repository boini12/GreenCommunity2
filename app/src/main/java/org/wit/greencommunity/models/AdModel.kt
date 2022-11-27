package org.wit.greencommunity.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AdModel( var id: Long = 0L,
    var title:String="", var description:String="", var price:Double=0.00,
    var isFree:Boolean=false) : Parcelable
