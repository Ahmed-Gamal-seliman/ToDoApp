package com.example.todolist.DataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import java.lang.reflect.Array

class TaskViewModel (application: Application) : AndroidViewModel(application) {

    val repository:Repository

    init {
        repository= Repository(application)
    }


    fun insertTask(task:Task)
    {
        repository.insertTask(task)
    }

    fun deleteTask(id:Int?)
    {
        repository.deleteTask(id)
    }

    fun upDateTask(id:Int?)
    {
        repository.upDateTask(id)
    }



    fun getAllTasks(): LiveData<MutableList<Task>>?
    {
        return repository.getAllTasks()
    }


}