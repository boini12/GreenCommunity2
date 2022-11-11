package org.wit.greencommunity.models

data class UserModel(var username:String="", var password:String="", var adList:List<AdModel>)
