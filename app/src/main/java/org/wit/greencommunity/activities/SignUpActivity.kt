package org.wit.greencommunity.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CompletableDeferred
import org.wit.greencommunity.R
import org.wit.greencommunity.databinding.ActivitySignUpBinding
import org.wit.greencommunity.main.MainApp
import timber.log.Timber
import timber.log.Timber.i

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        i("SignUpActivity has started")

        binding.btnSignUp.setOnClickListener(){

            auth.createUserWithEmailAndPassword(binding.email.text.toString(), binding.password.text.toString()).addOnCompleteListener(this, OnCompleteListener { task ->
               if(task.isSuccessful){
                   Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show()
                   val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                   startActivity(intent)
                   finish()
               }else {
                   Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()
               }
            })
        }

    }

}