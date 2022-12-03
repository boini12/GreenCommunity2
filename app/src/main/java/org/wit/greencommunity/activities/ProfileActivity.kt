package org.wit.greencommunity.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.auth.User
import com.squareup.picasso.Picasso
import org.wit.greencommunity.R
import org.wit.greencommunity.adapter.showImagePicker
import org.wit.greencommunity.databinding.ActivityProfileBinding
import org.wit.greencommunity.main.MainApp
import org.wit.greencommunity.models.UserModel
import timber.log.Timber
import timber.log.Timber.i

/**
 * This is the ProfileActivity of the GreenCommunity Application
 * Here a user can see all his current information that is saved in Firebase
 * A user has the chance to make changes to his/her profile and those changes are updated in Firebase
 */

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    lateinit var app: MainApp
    private lateinit var auth : FirebaseAuth
    private lateinit var userModel : UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAdd)

        Timber.i("ProfileActivity has started")

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

        userModel = UserModel(binding.username.text.toString(), binding.email.text.toString(), "", null)

        binding.btnChange.setOnClickListener{
            if (user != null) {
                if(userModel.username != user.displayName && userModel.email != user.email){
                    val profileUpdates = userProfileChangeRequest {
                        displayName = userModel.username
                    }
                    user!!.updateProfile(profileUpdates)

                    user!!.updateEmail(userModel.email)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                i("User has been successfully updated")
                            }
                        }
                }else if(userModel.email != user.email && userModel.username == user.displayName){
                    user!!.updateEmail(userModel.email)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                i("Email has been successfully updated")
                            }
                        }
                }else if(userModel.username != user.displayName && userModel.email == user.email){
                    val profileUpdates = userProfileChangeRequest {
                        displayName = userModel.username
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                i("Username has been successfully updated")
                            }
                        }
                }else{
                    i("Nothing has changed")
                }
            }
        }





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