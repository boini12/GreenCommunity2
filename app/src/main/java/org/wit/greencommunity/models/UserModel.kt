package org.wit.greencommunity.models

import android.net.Uri

data class UserModel(var username:String="", var email:String="", var password:String="", var photo: Uri?)
