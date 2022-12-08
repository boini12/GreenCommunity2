package org.wit.greencommunity.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.wit.greencommunity.R
import org.wit.greencommunity.databinding.ActivityAdBinding
import org.wit.greencommunity.main.MainApp
import org.wit.greencommunity.models.AdModel
import timber.log.Timber.i

/**
 *  This is the AdActivity of the GreenCommunity App
 *  this activity is used to add a new activity to the app and also add it to a users account
 *  [writeNewAd] pushes the new ad with its relevant information to the realtime database from firbase
 */

class AdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdBinding
    lateinit var app: MainApp
    var ad = AdModel()
    private lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        database = FirebaseDatabase.getInstance("https://greencommunity-219d2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("posts")
        auth = FirebaseAuth.getInstance()

        i("AdActivity has started")

        if(intent.hasExtra("ad_edit")) {
            ad = intent.extras?.getParcelable("ad_edit")!!
            binding.adTitle.setText(ad.title)
            binding.adDescription.setText(ad.description)
            binding.adFree.isChecked = ad.isFree
            binding.adFree.text = ad.price.toString()
        }

        binding.btnAdd.setOnClickListener{
            ad.id = app.ads.getId()
            i("ID is: $ad.id")
            ad.title = binding.adTitle.text.toString()
            ad.description = binding.adDescription.text.toString()

            binding.adFree.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked){
                    Toast.makeText(this, isChecked.toString(), Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, isChecked.toString(), Toast.LENGTH_LONG).show()
                }
            }

            ad.isFree = binding.adFree.isChecked
            if(ad.isFree){
                ad.price = 0.0
            }else{
                ad.price = binding.adPrice.text.toString().toDouble()
            }


            if(ad.title.isNotEmpty()){
                writeNewAd()
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

    /**
     * For this method I used a changed version of a method shown in this link
     * Link: https://www.folkstalk.com/tech/how-to-prevent-overwriting-existing-data-in-firebase-with-solutions/
     * Last opened: 05.12.2022
     */

    private fun writeNewAd(){
        val newAd = AdModel(ad.id, ad.title, ad.description, ad.price, ad.isFree)
        database.child(newAd.id.toString()).push().setValue(newAd)
        database.child(newAd.id.toString()).child("user").setValue(auth.currentUser!!.uid)
    }

}
