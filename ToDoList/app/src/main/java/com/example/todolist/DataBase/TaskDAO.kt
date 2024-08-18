package com.example.todolist.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TaskDAO {

    @Insert
    fun insertTask(task:Task)

    @Query("DELETE FROM Task WHERE id = :id")

    fun deleteTask(id:Int?)

    @Query("UPDATE Task SET status = 'Done' WHERE id=:id")
    fun upDateTask(id:Int?)

    @Query("select * from Task")

    fun getAllTasks(): LiveData<MutableList<Task>>?





}