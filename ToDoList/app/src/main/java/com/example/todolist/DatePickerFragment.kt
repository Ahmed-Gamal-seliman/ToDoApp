package com.example.todolist

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment():DialogFragment(),DatePickerDialog.OnDateSetListener {

    private var onDatePickerClickSetted:OnDataPickerClickSettedListener?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onDatePickerClickSetted= context as MainActivity
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it.
        return DatePickerDialog(requireContext(), this, year, month, day)

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onDatePickerClickSetted?.onDataPickerClickSetted(year,month,dayOfMonth)
    }

    interface OnDataPickerClickSettedListener
    {
        fun onDataPickerClickSetted(year: Int, month: Int, dayOfMonth: Int)
    }
}