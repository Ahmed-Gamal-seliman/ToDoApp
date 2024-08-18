package com.example.todolist.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.reactivex.annotations.NonNull

@Entity
class Task {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id:Int =0

    @ColumnInfo
    var title:String = ""

    @ColumnInfo
    var time:String= ""

    @ColumnInfo
    var date:String=""
    @ColumnInfo
    var status:String="Pending"
}