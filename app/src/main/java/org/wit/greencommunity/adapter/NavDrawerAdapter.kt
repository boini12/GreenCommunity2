package org.wit.greencommunity.adapter

import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso


fun adjustNavHeader(user: FirebaseUser, imageView : ImageView, textView: TextView){

    if(user.photoUrl != null){
        Picasso.get()
            .load(user?.photoUrl)
            .into(imageView)
    }


    user.let {
        textView.setText(user?.displayName)
    }

}