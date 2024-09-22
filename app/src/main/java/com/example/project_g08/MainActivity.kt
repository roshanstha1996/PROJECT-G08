package com.example.project_g08

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g08.databinding.ActivityMainBinding
import com.google.gson.Gson

data class User(val name: String)

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserName()

        binding.btnContinue.setOnClickListener {
            val name = binding.etName.text.toString()
            if (name.isNotEmpty()) {
                saveUser(User(name))
                val intent = Intent(this@MainActivity, WelcomeActivity::class.java)

                intent.putExtra("username", binding.etName.text.toString())

                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUser(user: User) {
        val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("username", Gson().toJson(user))
            apply()
        }
    }

    private fun loadUserName() {
        val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE) //shared pref initialization
        val userJson = sharedPref.getString("username", null) //json initialization
        if (userJson != null) {
            val intent = Intent(this@MainActivity, WelcomeActivity::class.java)

            val user = Gson().fromJson(userJson, User::class.java)

            intent.putExtra("username", user.name)
            startActivity(intent)
        }
    }
}
