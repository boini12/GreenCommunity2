package org.wit.greencommunity.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import org.wit.greencommunity.databinding.ActivityLoginBinding
import org.wit.greencommunity.main.MainApp
import timber.log.Timber

/**
 * The Login activity of the GreenCommunity application
 * Here the user can enter an email and a password to login
 * the credentials will be compared to the firebase database
 * if correctly entered then the user will be logged in and redirected to the HomeActivity otherwise an error message via Toast will be displayed
 */

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        Timber.i("SignUpActivity has started")


        binding.btnSignUp.setOnClickListener(){

            /**
             * For the login method I used code from this website:
             * Link: https://blog.mindorks.com/firebase-login-and-authentication-android-tutorial [section: Login a user with email and password]
             * Last opened: 29.11.2022
             */

            auth.signInWithEmailAndPassword(binding.email.text.toString(), binding.password.text.toString()).addOnCompleteListener(this, OnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                }
            })
        }

    }

}