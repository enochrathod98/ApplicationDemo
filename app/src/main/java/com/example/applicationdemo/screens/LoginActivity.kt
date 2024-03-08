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
import com.example.applicationdemo.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity(), LoginRegisterView.LoginRegisterViewListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (!intent.hasExtra(getString(R.string.isfromregister))) {
            val isFromRegister = intent.getBooleanExtra(getString(R.string.isfromregister), false)
            if (!isFromRegister) {
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val intent = Intent(
                        this,
                        MainActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()
        binding.loginView.listener = this
        binding.loginView.setButtonText(getString(R.string.login))

        binding.registrationTxt.setOnClickListener {
            val intent = Intent(
                this,
                RegistrationActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    // Getting Users Information
                    val currentUser = auth.currentUser
                    // Getting Encrypted Password form Firebase Realtime database
                    val userRef = database.getReference(getString(R.string.users)).child(currentUser!!.uid)
                    userRef.child(getString(R.string.encryptedpassword)).get().addOnSuccessListener { snapshot ->
                        val encryptedPassword = snapshot.value as String
                        val decryptedPassword = CommanUtils.decryptPassword(encryptedPassword)
                        // Checking the Encrypted and Decrypted Values
                        if (decryptedPassword == password) {
                            Toast.makeText(this,
                                getString(R.string.login_successful), Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "signInWithEmail:success")
                            binding.progressBar.visibility = View.GONE
                            val intent = Intent(
                                this,
                                MainActivity::class.java
                            )
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this,
                                getString(R.string.invalid_credentials), Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { exception ->
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            getString(R.string.failed_to_retrieve_password, exception.message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    binding.progressBar.visibility = View.GONE
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        getString(R.string.login_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    override fun onButtonClicked(email: String, password: String) {
        if (CommanUtils.isValid(email, password,baseContext)) {
            binding.progressBar.visibility = View.VISIBLE
            login(email, password)
        } else {
            Toast.makeText(
                baseContext,
                getString(R.string.please_enter_correct_email_and_password),
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}