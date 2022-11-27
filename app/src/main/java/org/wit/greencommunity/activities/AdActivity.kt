package org.wit.greencommunity.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import org.wit.greencommunity.R
import org.wit.greencommunity.databinding.ActivityAdBinding
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

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("AdActivity has started")

        if(intent.hasExtra("ad_edit")) {
            ad = intent.extras?.getParcelable("ad_edit")!!
            binding.adTitle.setText(ad.title)
            binding.adDescription.setText(ad.description)
            binding.adFree.isChecked = ad.isFree
            binding.adFree.text = ad.price.toString()
        }

        binding.btnAdd.setOnClickListener(){
            ad.title = binding.adTitle.text.toString()
            ad.description = binding.adDescription.text.toString()
            ad.isFree = binding.adFree.isActivated
            if(!ad.isFree){
                binding.adPrice.isEnabled
            }
            if(ad.title.isNotEmpty()){
                app.ads.create(ad.copy())
                setResult(RESULT_OK)
                finish()
            }else{
                Snackbar.make(it, "Please Enter a Title", Snackbar.LENGTH_LONG).show()
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_cancel ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}