package com.example.project_g08

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g08.databinding.ActivityWelcomeBinding
import com.google.gson.Gson

class WelcomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserName() // loads the name entered in the main activity

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LessonListActivity::class.java)
            startActivity(intent)
        }

        binding.btnReset.setOnClickListener {
            val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                clear() // clears all the data
                apply()
            }

            // goes to the main activity after clearing data
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserName() {
        val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE) // shared pref initialization
        val userJson = sharedPref.getString("username", null) // json initialization
        if (userJson != null) {
            val user = Gson().fromJson(userJson, User::class.java)
            binding.tvWelcome.text = "Welcome back, ${user.name}!"
        }
        updateProgress()
    }

    private fun updateProgress(){
        val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE) // shared pref initialization
        val completedPercentString = sharedPref.getString("completedPercent", null) // json initialization
        val completedCourseCountString = sharedPref.getString("completedCourseCount", null)
        val remainingCourseCountString = sharedPref.getString("remainingCourseCount", null)

        val completedPercent: Double = if (completedPercentString == null) {
            0.0
        } else {
            completedPercentString.toDouble() * 100.0
        }

        val completedCourseCount = completedCourseCountString?.toInt() ?: 0
        val remainingCourseCount = remainingCourseCountString?.toInt() ?: 0

        binding.tvProgress.text = "You've completed ${completedPercent.toInt()}% of the course"
        binding.tvLessonCompleted.text = "Lesson Completed: $completedCourseCount"
        binding.tvLessonRemaining.text = "Lesson Remaining: $remainingCourseCount"
    }

    override fun onResume() {
        super.onResume()
        updateProgress()
    }
}
