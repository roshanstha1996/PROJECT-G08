package com.example.project_g08

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_g08.databinding.ActivityLessonListBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class LessonListActivity : AppCompatActivity(), ClickImageInterface {

    lateinit var binding: ActivityLessonListBinding

    var totalCompletedPercent: Double = 0.0
    lateinit var adapter: MyRecyclerViewAdapter
    val REQUEST_CODE = 1 //variable initialization of the lesson updated

    var myLessonList: MutableList<Lesson> = mutableListOf(
        Lesson(1, "C# Tutorial For Beginners - Learn C# Basics", "1H 10Min", "This is a C# .NET tutorial part 1, and you will learn this if you're a complete beginner to programming and you're interested in wide range of software development.", "https://youtu.be/gfkTfcpWqAY?si=8Dl8JF8fXMDxczkP", false),
        Lesson(2, "C# Classes Tutorial", "14Min", "Learn the fundamentals of creating and using classes in C# with this comprehensive tutorial.", "https://youtu.be/ZqDtPFrUonQ?si=N6uGcgo_a8FNo4pj", false),
        Lesson(3, "C# Methods Tutorial", "25 Min", "Master the essentials of creating and using methods in C# with this concise tutorial.", "https://youtu.be/56bbjE4assU?si=pM4_shX0o0VrRs6X", false),
        Lesson(4, "C# Events and Delegates Made Simple", "32 Min", "Understand the basics of C# events and delegates with this easy-to-follow tutorial", "https://youtu.be/jQgwEsJISy0?si=R-OnpeKH7pb8lkht", false),
        Lesson(5, "Unit Testing C# Code - Tutorial for Beginners", "45 Min", "Explore the fundamentals of unit testing in C# and learn how to write effective tests for your code in this detailed beginner's tutorial.", "https://youtu.be/HYrXogLj7vg?si=PO1lTLlkFt-IxHXN", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLessonListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Load isCompleted status for each lesson
        val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
        for (lesson in myLessonList) {
            lesson.isCompleted = sharedPref.getBoolean("lesson_${lesson.sno}_isCompleted", false)
        }

        // create an instance of the adapter
        adapter = MyRecyclerViewAdapter(myLessonList, this)

        binding.myRecyclerView.adapter = adapter

        // required to populate into the Recycler View
        binding.myRecyclerView.layoutManager = LinearLayoutManager(this)

        // add a line between each item in the list
        binding.myRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    // when clicked on text view go to the detail screen
    override fun lessonDetailClicked(position: Int) {
        val lessonDetail: Lesson = Lesson(myLessonList[position].sno, myLessonList[position].lessonName, myLessonList[position].length, myLessonList[position].description, myLessonList[position].link, myLessonList[position].isCompleted)
        val intent: Intent = Intent(this@LessonListActivity, LessonDetailActivity::class.java)

        intent.putExtra("lessonDetail", lessonDetail)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val lessonDetail: Lesson = data?.getSerializableExtra("updatedLesson") as Lesson

            var completedCount: Int = 0

            for (x: Lesson in myLessonList) {
                if (x.sno == lessonDetail.sno) {
                    x.isCompleted = lessonDetail.isCompleted
                }
                if (x.isCompleted) {
                    completedCount++
                }
            }

            totalCompletedPercent = (completedCount / 5.0) * 100.0

            val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("completedPercent", Gson().toJson(totalCompletedPercent / 100))
                putString("completedCourseCount", Gson().toJson(completedCount))
                putString("remainingCourseCount", Gson().toJson(5 - completedCount))

                //saves the isCompleted status
                for (lesson in myLessonList) {
                    putBoolean("lesson_${lesson.sno}_isCompleted", lesson.isCompleted)
                }
                apply()
            }

            //pop up after completing lesson
            val snackbar = Snackbar.make(binding.root, "You completed to a lesson!", Snackbar.LENGTH_SHORT)
            snackbar.show()
            adapter.notifyDataSetChanged()
        }
    }

    override fun markLessonCompleted(position: Int) {
        Log.d("roshan", "test")
        myLessonList[position].lessonName = "This is a test"

        val sharedPref = getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
        val lessonCompletedString = sharedPref.getString("lessonCompleted", null)
        val isLessonCompleted: Boolean = lessonCompletedString?.toBoolean() ?: false

        Log.d("roshan", isLessonCompleted.toString())
        myLessonList[position].isCompleted = isLessonCompleted

        Log.d("roshan", myLessonList[position].lessonName)
        Log.d("roshan", myLessonList[position].description)

        // Update the item in the specified position
        adapter.notifyItemChanged(position)
        adapter.notifyDataSetChanged()
    }
}
