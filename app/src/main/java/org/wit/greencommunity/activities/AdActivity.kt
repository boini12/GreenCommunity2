package org.wit.greencommunity.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.wit.greencommunity.databinding.ActivityAdBinding
import org.wit.greencommunity.databinding.ActivityMainBinding
import org.wit.greencommunity.main.MainApp
import org.wit.greencommunity.models.AdModel
import timber.log.Timber.i

class AdActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAdBinding
    lateinit var app: MainApp
    var ad = AdModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        i("AdActivity has started")

        binding.btnAdd.setOnClickListener(){
            ad.title = binding.adTitle.text.toString()
            ad.description = binding.adDescription.text.toString()
            ad.isFree = binding.adFree.isActivated
            if(!ad.isFree){
                binding.adPrice.isEnabled
            }
        }


    }
}