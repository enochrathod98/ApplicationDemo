package com.example.applicationdemo.screens

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationdemo.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        database = FirebaseDatabase.getInstance()



        binding.registrationTxt.setOnClickListener {
            val intent = Intent(
                this,
                RegistrationActivity::class.java
            )
            startActivity(intent)
            finish()
        }

        binding.buttonLogin.setOnClickListener {
            val email: String = binding.editTextEmailLogin.text.toString()
            val password: String = binding.editTextPasswordLogin.text.toString()
            if (isValid(email, password)) {
                binding.progressBar.visibility = View.VISIBLE
                login(email, password)
            } else {
                Toast.makeText(
                    baseContext,
                    "Please enter correct Email and Password",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val currentUser = auth.currentUser
                    val userRef = database.getReference("users").child(currentUser!!.uid)
                    userRef.child("encryptedPassword").get().addOnSuccessListener { snapshot ->
                        val encryptedPassword = snapshot.value as String
                        val decryptedPassword = decryptPassword(encryptedPassword)
                        if (decryptedPassword == password) {
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            Log.d(TAG, "signInWithEmail:success")
                            binding.progressBar.visibility = View.GONE
                            val intent = Intent(
                                this,
                                MainActivity::class.java
                            )
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { exception ->
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            "Failed to retrieve password: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    binding.progressBar.visibility = View.GONE
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Login failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }


    fun isValid(email: String, password: String): Boolean {
        // Check for empty email or password
        if (email.isEmpty() || password.isEmpty()) {
            return false
        }

        // Validate email format
        val emailPattern = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
        if (!email.matches(emailPattern)) {
            return false
        }

        // Validate password for at least one special character and one capital letter
        val specialCharPattern = "[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]".toRegex()
        val capitalLetterPattern = "[A-Z]".toRegex()

        val hasSpecialChar = specialCharPattern.containsMatchIn(password)
        val hasCapitalLetter = capitalLetterPattern.containsMatchIn(password)

        return hasSpecialChar && hasCapitalLetter
    }

    // Function to decrypt the password
    private fun decryptPassword(encryptedPassword: String): String {
        // Decode the Base64 string to get the combined byte array
        val decodedBytes = Base64.decode(encryptedPassword, Base64.DEFAULT)
        // Extract the IV and cipher text from the combined byte array
        val iv = decodedBytes.copyOf(12)
        val cipherText = decodedBytes.copyOfRange(12, decodedBytes.size)
        // Generate the key for decryption
        val key: SecretKey = generateKey()
        // Initialize the cipher with AES decryption in GCM mode
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        // Specify the parameters including the IV for decryption
        val spec = GCMParameterSpec(128, iv)
        // Initialize the cipher with the generated key and parameters for decryption
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        // Decrypt the cipher text and return the result as a string
        val decryptedBytes = cipher.doFinal(cipherText)
        return String(decryptedBytes)
    }

    // Function to generate a random key
    private fun generateKey(): SecretKey {
        val key = ByteArray(16) // Create a byte array of size 16 bytes (128 bits)
        return SecretKeySpec(key, "AES") // Create a SecretKey object from the byte array and return
    }

}