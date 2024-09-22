package com.example.project_g08

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_g08.databinding.ActivityLessonDetailBinding
import com.example.project_g08.databinding.CustomRowLayoutBinding

class MyRecyclerViewAdapter(val myItems:MutableList<Lesson>, val clickInterface: ClickImageInterface) : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {

    // Configure the binding variable for the custom_row_layout.xml file
    inner class ViewHolder(val binding: CustomRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return myItems.size
    }


    // The “holder” parameter provides access to the row layout’s binding variable
    // The “position” parameter indicates which item in the data source we creating a row for
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("", "Creating UI for row # ${position} ${myItems[position]}")
        // If the data source was a list of Student objects, then use currData:Student
        val currData: Lesson = this.myItems[position]
        //binding the textview
        holder.binding.tvRowLine1.text = currData.lessonName
        holder.binding.tvRowLine2.text = currData.length
        //if the isCompleted status is true then visible, otherwise gone
        if(currData.isCompleted){
            Log.d("", "Creating UI for row #1")
            holder.binding.isCompletedImage.visibility = View.VISIBLE
        }else{
            Log.d("", "Creating UI for row #2")
            holder.binding.isCompletedImage.visibility = View.GONE
        }

        //if clicked in main row line
        holder.binding.line1.setOnClickListener {
            clickInterface.lessonDetailClicked(position)
        }

        //if clicked in top text view
        holder.binding.tvRowLine1.setOnClickListener {
            clickInterface.lessonDetailClicked(position)
        }

        //if clicked in second text view
        holder.binding.tvRowLine2.setOnClickListener {
            clickInterface.lessonDetailClicked(position)
        }

    }
}