package com.example.applicationdemo.screens

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationdemo.R
import com.example.applicationdemo.comman.CommanUtils
import com.example.applicationdemo.databinding.ActivityRegistrationBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase


class RegistrationActivity : AppCompatActivity(), LoginRegisterView.LoginRegisterViewListener {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        binding.registrationView.listener = this
        binding.registrationView.setButtonText(getString(R.string.register))


        binding.loginTxt.setOnClickListener {
            val intent = Intent(
                this,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                binding.progressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val currentUser = auth.currentUser
                    val encryptedPassword = CommanUtils.encryptPassword(password)
                    val userRef = database.getReference(getString(R.string.users)).child(currentUser!!.uid)
                    userRef.child(getString(R.string.encryptedpassword)).setValue(encryptedPassword)
                    Toast.makeText(this,
                        getString(R.string.registration_successful), Toast.LENGTH_SHORT).show()
                    val intent = Intent(
                        this,
                        LoginActivity::class.java
                    )
                    intent.putExtra(getString(R.string.isfromregister), true)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        getString(R.string.registration_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    override fun onButtonClicked(email: String, password: String) {
        if (CommanUtils.isValid(email, password, baseContext)) {
            binding.progressBar.visibility = View.VISIBLE
            createUser(email, password)
        } else {
            Toast.makeText(
                baseContext,
                getString(R.string.please_enter_correct_email_and_password),
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}

