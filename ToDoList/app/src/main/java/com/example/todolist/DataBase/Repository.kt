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

        TaskRoomDatabase.databaseWriteExecutor.execute {
            taskDao?.insertTask(task)
        }


    }


    fun deleteTask(id:Int?)
    {
        TaskRoomDatabase.databaseWriteExecutor.execute {
            taskDao?.deleteTask(id)
        }
    }

    fun upDateTask(id:Int?)
    {
        TaskRoomDatabase.databaseWriteExecutor.execute {
            taskDao?.upDateTask(id)
        }
    }

    fun getAllTasks(): LiveData<MutableList<Task>>?
    {
        TaskRoomDatabase.databaseWriteExecutor.execute {
            allTasks= taskDao?.getAllTasks()
        }
        return allTasks
    }


}