package org.wit.greencommunity.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.greencommunity.databinding.ActivityMainBinding
import org.wit.greencommunity.main.MainApp
import timber.log.Timber.i

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        i("GreenCommunity Application has been started")

        binding.btnExplore.setOnClickListener(){
            i("Explore Button pressed")

        }
    }
}