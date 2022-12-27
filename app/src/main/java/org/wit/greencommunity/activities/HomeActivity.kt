package org.wit.greencommunity.activities

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ToggleButton
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import org.wit.greencommunity.R
import org.wit.greencommunity.databinding.ActivityMainBinding
import org.wit.greencommunity.main.MainApp
import org.wit.greencommunity.models.UserModel
import timber.log.Timber.i


/**
 * The main activity of the GreenCommunity application
 * From here the user can explore their area through a button
 * User will get redirected if no currentUser is detected -> Redirected to LoginOrSignUpActivity
 * If a user is logged in and wants to logout a button on the menu is available that will then call the signOut() method from Firebase
 * From here the user can also go to his profile
 */
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    lateinit var app : MainApp
    private lateinit var toolbar : Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView : NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setSupportActionBar(binding.toolbar)
        toolbar = findViewById(R.id.toolbar)

       binding.apply {
           toggle = ActionBarDrawerToggle(this@HomeActivity, drawerLayout, 0 ,0)
       }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)


        auth = FirebaseAuth.getInstance()

        app = application as MainApp

        i("GreenCommunity Application has been started")

        binding.homeActivity.btnExplore.setOnClickListener(){
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
            menu.getItem(1).isVisible = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_logout -> {
                auth.signOut()
                finish()
                i("User has been logged out")
                recreate()

            }

            R.id.item_profile -> {
                intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.profile -> {
                intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.ads -> {
                TODO("needs to still be implemented")
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}