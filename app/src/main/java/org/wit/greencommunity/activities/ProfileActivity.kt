package org.wit.greencommunity.activities

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import org.wit.greencommunity.R
import org.wit.greencommunity.databinding.ActivityProfileBinding
import org.wit.greencommunity.main.MainApp
import timber.log.Timber
import java.lang.System.load

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    lateinit var app: MainApp
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAdd)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        app = application as MainApp

        user?.let {
            binding.username.setText(user.displayName)
            binding.email.setText(user.email)

        }
        if (user != null) {
            Picasso.get()
                .load(user.photoUrl)
                .into(binding.profileImg)
        }

        Timber.i("ProfileActivity has started")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}