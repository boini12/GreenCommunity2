package org.wit.greencommunity.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.wit.greencommunity.R
import org.wit.greencommunity.databinding.ActivityMainBinding
import org.wit.greencommunity.main.MainApp
import timber.log.Timber.i


/**
 * The main activity of the GreenCommunity application
 * From here the user can explore their area through a button
 * User will get redirected if no currentUser is detected -> Redirected to LoginOrSignUpActivity
 * To navigate through the possible views a Navigation Drawer is implemented
 */

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    lateinit var app : MainApp
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appToolbar.toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.appToolbar.toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.bringToFront()
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

    /*
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


     */

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