package org.wit.greencommunity.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import org.wit.greencommunity.R
import org.wit.greencommunity.adapter.showImagePicker
import org.wit.greencommunity.databinding.ActivitySignUpBinding
import org.wit.greencommunity.main.MainApp
import timber.log.Timber.i

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var img : Uri
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAdd)

        img = Uri.EMPTY

        app = application as MainApp

        database = Firebase.database.reference

        i("SignUpActivity has started")


        binding.btnSignUp.setOnClickListener(){

            /**
             * For the login method I used code from this website:
             * Link: https://blog.mindorks.com/firebase-login-and-authentication-android-tutorial [section: Register a user with email and password]
             * Last opened: 29.11.2022
             */

            auth.createUserWithEmailAndPassword(binding.email.text.toString(), binding.password.text.toString()).addOnCompleteListener(this, OnCompleteListener { task ->
               if(task.isSuccessful){
                   if(img != null){
                       addUserImgAndUsername(img)
                   }else{
                       addUsername()
                   }
                   Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                   val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                   startActivity(intent)
                   finish()
               }else {
                   Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
               }
            })
        }
        binding.addImage.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
        }
        registerImagePickerCallback()

    }


    private fun addUserImgAndUsername(image : Uri){
        if(auth.currentUser != null){
            val profileUpdates = userProfileChangeRequest {
                photoUri = image
                i("photoURI: $photoUri")
                displayName = binding.username.text.toString()
            }

            auth.currentUser!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        i("User has been updated")
                    }
                }
        }
    }

    private fun addUsername(){
        if(auth.currentUser != null){
            val profileUpdates = userProfileChangeRequest {
                displayName = binding.username.text.toString()
            }

            auth.currentUser!!.updateProfile(profileUpdates)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        i("Username has been updated")
                    }
                }
        }
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
                            binding.addImage.setText(R.string.change_profile_picture)
                        }
                        // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}