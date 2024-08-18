package com.example.todolist

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.todolist.DataBase.Task
import com.example.todolist.DataBase.TaskViewModel

class Application:Application() {

    companion object{
        var taskViewModel:TaskViewModel?=null
    }
    override fun onCreate() {
        super.onCreate()

        taskViewModel= TaskViewModel(this)




    }
}