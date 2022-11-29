package org.wit.greencommunity.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import com.google.firebase.auth.FirebaseAuth
import org.wit.greencommunity.R
import org.wit.greencommunity.databinding.ActivityMainBinding
import org.wit.greencommunity.main.MainApp
import timber.log.Timber.i


/**
 * The main activity of the GreenCommunity application
 * From here the user can explore their area through a button
 * User will get redirected if no currentUser is detected -> Redirected to LoginOrSignUpActivity
 * If a user is logged in and wants to logout a button on the menu is available that will then call the signOut() method from Firebase
 */
class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAdd)

        auth = FirebaseAuth.getInstance()

        app = application as MainApp

        i("GreenCommunity Application has been started")

        binding.btnExplore.setOnClickListener(){
            i("Explore Button pressed")

            intent = if(auth.currentUser != null){
                Intent(this@HomeActivity, AdListActivity::class.java)
            }else{
                Intent(this@HomeActivity, LoginOrSignUpActivity::class.java)
            }

            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home,menu)
        if(auth.currentUser != null && menu != null){
            menu.getItem(0).isVisible = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                auth.signOut()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}