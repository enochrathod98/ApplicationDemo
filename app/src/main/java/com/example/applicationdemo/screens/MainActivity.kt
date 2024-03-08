package com.example.applicationdemo.screens

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationdemo.R
import com.example.applicationdemo.models.UserViewModel
import com.example.applicationdemo.adapter.UserAdapter
import com.example.applicationdemo.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val userViewModel: UserViewModel by viewModels()
    lateinit var userAdapter: UserAdapter
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        setSupportActionBar(binding.toolbar)

        userAdapter = UserAdapter()
        binding.userRv.adapter = userAdapter
        GlobalScope.launch {
            userViewModel._userLiveData.collect {
                runOnUiThread {
                    if (it.isEmpty()){
                        binding.progressBar.visibility = View.VISIBLE
                    }else{
                        binding.progressBar.visibility = View.GONE
                    }
                    userAdapter.planes = it
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.custom_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                // Handle custom action button click
                auth.signOut()
                val intent = Intent(
                    this,
                    LoginActivity::class.java
                )
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}