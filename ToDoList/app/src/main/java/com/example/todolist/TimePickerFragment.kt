package com.example.todolist

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.text.DateFormat
import java.util.Calendar

class TimePickerFragment: DialogFragment(), TimePickerDialog.OnTimeSetListener {

   private var timePickerOnSetClick:TimePickerOnSetListener?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        timePickerOnSetClick = context as MainActivity
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calender= Calendar.getInstance()
        val hour= calender.get(Calendar.HOUR_OF_DAY)
        val minute= calender.get(Calendar.MINUTE)

        return TimePickerDialog(context,this,hour,minute,false)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Toast.makeText(context,hourOfDay.toString(),Toast.LENGTH_SHORT).show()
        var AM_PM:String = ""
        if(hourOfDay < 12)
            AM_PM = "AM"
         else
            AM_PM = "PM"


        timePickerOnSetClick?.onTimePickerSettedListener(hourOfDay,minute,AM_PM)
    }

    interface TimePickerOnSetListener
    {
        fun onTimePickerSettedListener(hourOfDat:Int,minute:Int,AM_PM:String)
    }
}