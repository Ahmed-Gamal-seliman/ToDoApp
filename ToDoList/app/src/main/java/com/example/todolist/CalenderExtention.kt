package com.example.todolist

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.formatTime():String
{
    return SimpleDateFormat("hh:mm a").format(time)

}

fun Calendar.formatDate():String
{
    return SimpleDateFormat("dd/MM/YYYY").format(time)
}