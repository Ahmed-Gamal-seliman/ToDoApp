package com.example.todolist

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.graphics.scaleMatrix
import com.example.todolist.DataBase.Task
import com.example.todolist.databinding.FragmentNewTodoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class FragmentAddToDo: BottomSheetDialogFragment() {

    lateinit var binding:FragmentNewTodoBinding



    private var addItemClick:AddItemClickListener?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)


        addItemClick =context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTodoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        handleFloatingActionButtonSaveClicked()

        handleOnSelectedTimeClicked()

        handleOnSelectedDateClicked()
    }

    private fun handleOnSelectedTimeClicked() {
        binding.tvSelectTime.setOnClickListener {

            showTimePicker()


        }
    }

    private fun showTimePicker() {

        val time = Calendar.getInstance()
        TimePickerDialog(
            context,
            object: TimePickerDialog.OnTimeSetListener
            {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    time.set(0,0,0,hourOfDay,minute)
                   binding.tvTime.text= time.formatTime()
                }

            },
            time.get(Calendar.HOUR_OF_DAY),
            time.get(Calendar.MINUTE),
            false
        ).show()
    }



    private fun handleOnSelectedDateClicked() {
        binding.tvSelectDate.setOnClickListener {

            showDatePicker()


        }
    }

    private fun showDatePicker() {

        val date = Calendar.getInstance()

            DatePickerDialog(
                requireContext(),
                object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(
                        view: DatePicker?,
                        year: Int,
                        month: Int,
                        dayOfMonth: Int
                    ) {

                        date.set(year,month,dayOfMonth)
                    binding.tvDate.text = date.formatDate()
                    }

                },
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)

                ).show()
    }


    private fun isvalidationSuccess(): Boolean {
        val validateTaskName:String?= validateTaskNameEntry(binding.TietEnterTask.text.toString())




        if(validateTaskName ==null)
            return true

        binding.TilEnterTask.error= validateTaskName

        return false
    }

    private fun handleFloatingActionButtonSaveClicked() {
        binding.fabSave.setOnClickListener{

            if(!isvalidationSuccess())
                return@setOnClickListener

            //val task:Task = Task(,.text.toString())
            val task:com.example.todolist.DataBase.Task= com.example.todolist.DataBase.Task()
            task.title= binding.TietEnterTask.text.toString()
            task.time=binding.tvTime.text.toString()
            task.date= binding.tvDate.text.toString()


            addItemClick?.addItemClick(task)
            com.example.todolist.Application.taskViewModel?.insertTask(task)

            //sharedViewModel?.addItem(task);
            dismiss()


        }
    }

    private fun validateTaskNameEntry(taskName:String):String? {
        if(taskName.trim().isEmpty())
            return "required task name"

        return null
    }


    interface AddItemClickListener
    {
        fun addItemClick(task:Task)
    }
}