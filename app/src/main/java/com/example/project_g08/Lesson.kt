package com.example.project_g08

import java.io.Serializable

class Lesson: Serializable {
    var sno: Int
    var lessonName:String
    var length:String
    var description:String
    var link: String
    var isCompleted: Boolean

    constructor(
        sno: Int,
        lessonName: String,
        length: String,
        description: String,
        link: String,
        isCompleted: Boolean
    ) {
        this.sno = sno
        this.lessonName = lessonName
        this.length = length
        this.description = description
        this.link = link
        this.isCompleted = isCompleted
    }
}