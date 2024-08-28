package com.example.todolist

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.DataBase.Task
import com.example.todolist.databinding.ActivityEditTaskBinding
import java.util.Calendar

class EditTask : AppCompatActivity() {

    lateinit var binding:ActivityEditTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleOnSelectedTimeClicked()
        handleOnSelectedDateClicked()

        binding.btnEdit.setOnClickListener()
        {
            val intent:Intent= intent
            val task = IntentCompat.getParcelableExtra(intent,"realTask",Task::class.java) as Task
            val position =intent.getIntExtra("position",0)
            Log.e("in edit position",position.toString())
            putUpdatedTaskInIntent(task,position)
        }




    }

    private fun getTaskToUpdate(): Task {
        val intent:Intent= intent
        val task = IntentCompat.getParcelableExtra(intent,"realTask",Task::class.java) as Task
        Log.e("task",task.id.toString())
//        Log.e("task", task.date)
//        Log.e("task", task.title)
        return Task()
    }

    private fun putUpdatedTaskInIntent(task:Task,position:Int) {


        if(binding.tvDateEt.text.isNotEmpty())
            task.date = binding.tvDateEt.text.toString()
        if(binding.tvTimeEt.text.isNotEmpty())
            task.time = binding.tvTimeEt.text.toString()
        if(binding.TietEnterTask.text?.isNotEmpty()!!)
            task.title = binding.TietEnterTask.text.toString()

        val intent:Intent = Intent()
        intent.putExtra("task",task)
        intent.putExtra("position",position)
        Log.e("in putUpdate fun",position.toString())
        setResult(RESULT_OK,intent)
        finish()
    }

    private fun handleOnSelectedTimeClicked() {
        binding.tvSelectTimeEt.setOnClickListener {

            showTimePicker()


        }
    }
    private fun handleOnSelectedDateClicked() {
        binding.tvSelectDateEt.setOnClickListener {

            showDatePicker()


        }
    }
    private fun showDatePicker() {

        val date = Calendar.getInstance()

        DatePickerDialog(
           this,
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(
                    view: DatePicker?,
                    year: Int,
                    month: Int,
                    dayOfMonth: Int
                ) {

                    date.set(year,month,dayOfMonth)
                    binding.tvDateEt.text = date.formatDate()
                }

            },
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH)

        ).show()
    }

    private fun showTimePicker() {

        val time = Calendar.getInstance()
        TimePickerDialog(
            this,
            object: TimePickerDialog.OnTimeSetListener
            {
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    time.set(0,0,0,hourOfDay,minute)
                    binding.tvTimeEt.text= time.formatTime()
                }

            },
            time.get(Calendar.HOUR_OF_DAY),
            time.get(Calendar.MINUTE),
            false
        ).show()
    }

}