package com.example.applicationdemo.screens

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.example.applicationdemo.R

class LoginRegisterView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val emailEditText: EditText
    private val passwordEditText: EditText
    private val submitButton: Button
    var listener: LoginRegisterViewListener? = null

    interface LoginRegisterViewListener {
        fun onButtonClicked(email: String, password: String)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_view, this, true)

        emailEditText = findViewById(R.id.editTextEmail_login)
        passwordEditText = findViewById(R.id.editTextPassword_login)
        submitButton = findViewById(R.id.buttonLogin)


        submitButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            listener?.onButtonClicked(email, password)
        }
    }

    fun setButtonText(text: String) {
        submitButton.text = text
    }

    fun getEmail(): String {
        return emailEditText.text.toString()
    }

    fun getPassword(): String {
        return passwordEditText.text.toString()
    }
}
