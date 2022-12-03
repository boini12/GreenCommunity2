package org.wit.greencommunity.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
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
    private lateinit var user : FirebaseUser
    private lateinit var userModel : UserModel
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var img : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAdd)

        Timber.i("ProfileActivity has started")

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        img = if(user.photoUrl != null){
            user.photoUrl!!
        }else{
            Uri.EMPTY

        }

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

        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                userModel.username = binding.username.text.toString()
            }

        })

        binding.btnChangeImg.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
        }
        registerImagePickerCallback()
        binding.btnChange.setOnClickListener{
            val profileUpdates = userProfileChangeRequest {
                photoUri = img
                displayName = binding.username.text.toString()
            }

            user!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Timber.i("User has been updated")
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

    private fun registerImagePickerCallback(){
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            i("image: $image")
                            img = image
                            i("img: $img")
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            Picasso.get()
                                .load(image)
                                .into(binding.profileImg)
                        }
                        // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }


}