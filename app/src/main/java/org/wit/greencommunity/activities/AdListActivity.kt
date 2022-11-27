package org.wit.greencommunity.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.greencommunity.R
import org.wit.greencommunity.adapter.AdAdapter
import org.wit.greencommunity.adapter.AdListener
import org.wit.greencommunity.databinding.ActivityAdListBinding
import org.wit.greencommunity.main.MainApp
import org.wit.greencommunity.models.AdModel

class AdListActivity : AppCompatActivity(), AdListener {
    
    lateinit var app: MainApp
    private lateinit var binding: ActivityAdListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = application as MainApp
        
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        binding.recyclerView.adapter = AdAdapter(app.ads.findAll(), this)
        
        
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, AdActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.ads.findAll().size)
            }
        }

    override fun onAdClick(ad: AdModel) {
        val launcherIntent = Intent(this, AdActivity::class.java)
        launcherIntent.putExtra("ad_edit", ad)
        getClickResult.launch(launcherIntent)
    }

    private val getClickResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            (binding.recyclerView.adapter)?.
            notifyItemRangeChanged(0, app.ads.findAll().size)
        }
    }
}
