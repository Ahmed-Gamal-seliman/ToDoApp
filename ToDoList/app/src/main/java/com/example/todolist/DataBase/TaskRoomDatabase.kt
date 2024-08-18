package com.example.todolist.DataBase

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskRoomDatabase: RoomDatabase() {

    abstract fun taskDao():TaskDAO

    companion object{
        @Volatile var INSTANCE:TaskRoomDatabase? =null
        private val numberOfThreads:Int =4
        val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(numberOfThreads)


        fun getDatabase(context: Context):TaskRoomDatabase?
        {
            if(INSTANCE ==null)
            {
                synchronized(TaskRoomDatabase::class)
                {
                    if(INSTANCE == null)
                    {
                        INSTANCE =Room.databaseBuilder(context.applicationContext,TaskRoomDatabase::class.java,
                            "Task_db")
                            .addCallback(roomCallBack)
                            .build()


                    }
                }
            }
            return INSTANCE;
        }

        val roomCallBack: RoomDatabase.Callback= object: RoomDatabase.Callback()
        {
            override fun onCreate(db: SupportSQLiteDatabase) {

                super.onCreate(db)
                databaseWriteExecutor.execute {
                    Log.e("db","execute")
                }
            }
        }

    }
}