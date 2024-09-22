package com.example.project_g08

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.project_g08.databinding.ActivityLessonDetailBinding
import com.google.gson.Gson

class LessonDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityLessonDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if(intent != null){
            val lessonDetail: Lesson = intent.getSerializableExtra("lessonDetail") as Lesson
            binding.lessonTitle.text = lessonDetail.lessonName
            binding.lessonLength.text = lessonDetail.length
            binding.lessonDescription.text = lessonDetail.description
            binding.btnWatchLesson.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(lessonDetail.link))
                startActivity(intent)
            }

            binding.btnMarkComplete.setOnClickListener {
                lessonDetail.isCompleted = true
                Log.d("roshan", lessonDetail.isCompleted.toString())

                //when the button is clicked it return the result
                val resultIntent = Intent()
                resultIntent.putExtra("updatedLesson", lessonDetail)
                setResult(Activity.RESULT_OK, resultIntent)
                //current activity cycle ends
                finish()
            }
        }
    }

}