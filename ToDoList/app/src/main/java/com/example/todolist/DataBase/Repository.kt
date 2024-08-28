package com.example.todolist.DataBase

import android.app.Application
import androidx.lifecycle.LiveData

class Repository(val application: Application) {
    private val taskDao:TaskDAO?
    private var allTasks: LiveData<MutableList<Task>>?=null

      var roomDb:TaskRoomDatabase?=null
          private set
    init {
        roomDb= TaskRoomDatabase.getDatabase(application)
        taskDao = roomDb?.taskDao()
        allTasks = taskDao?.getAllTasks()
    }


    fun insertTask(task: Task)
    {


            taskDao?.insertTask(task)



    }


    fun deleteTask(id:Int?)
    {

            taskDao?.deleteTask(id)


    }

    fun updateTaskStatus(id:Int?)
    {

            taskDao?.updateTaskStatus(id)

    }

    fun getAllTasks(): LiveData<MutableList<Task>>?
    {

            allTasks= taskDao?.getAllTasks()

        return allTasks
    }

    fun updateTask(task:Task)
    {
        taskDao?.updateTask(task)
    }


}