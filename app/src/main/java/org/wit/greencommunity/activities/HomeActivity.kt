package org.wit.greencommunity.activities

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import org.wit.greencommunity.R
import org.wit.greencommunity.adapter.adjustNavHeader
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

        val navHeader = binding.navView.getHeaderView(0)

        if(auth.currentUser != null){
            adjustNavHeader(auth.currentUser!!, navHeader.findViewById(R.id.nav_userImg), navHeader.findViewById(R.id.nav_username))
        }



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
            R.id.login -> {
                if(auth.currentUser == null){
                    item.isVisible = true
                    intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }else{
                    item.isVisible = false
                }
            }
            R.id.profile -> {
                if(auth.currentUser != null){
                    intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "You need to log in in order to see your profile", Toast.LENGTH_LONG).show()
                    intent = Intent(this, LoginOrSignUpActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.ads -> {
                TODO("needs to still be implemented")
            }
            R.id.logout -> {
                if(auth.currentUser != null){
                    item.isVisible = true
                    auth.signOut()
                    Toast.makeText(this, "Successfully logged out", Toast.LENGTH_LONG).show()
                    i("User has been logged out")
                    recreate()
                }else{
                    item.isVisible = false
                }
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}

