package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import com.example.todolist.DataBase.Task
import com.example.todolist.databinding.FragmentNewTodoBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentAddToDo: BottomSheetDialogFragment() {

    lateinit var binding:FragmentNewTodoBinding

    private  var onTimePickerClick:OnTimePickerClickListener?=null


    private var datePickerOnClick: DatePickerOnClickListener?=null

    private var addItemClick:AddItemClickListener?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        onTimePickerClick= context as MainActivity
        datePickerOnClick = context as MainActivity
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
            //call back to Main Activity

            onTimePickerClick?.onTimePickerClick()


        }
    }

    private fun handleOnSelectedDateClicked() {
        binding.tvSelectDate.setOnClickListener {
            //call back to Main Activity

            datePickerOnClick?.datePickerOnClick()


        }
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

            Log.e("save float","ok")
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

    interface OnTimePickerClickListener
    {
        fun onTimePickerClick()
    }

    interface DatePickerOnClickListener
    {
        fun datePickerOnClick()
    }

    interface AddItemClickListener
    {
        fun addItemClick(task:Task)
    }
}