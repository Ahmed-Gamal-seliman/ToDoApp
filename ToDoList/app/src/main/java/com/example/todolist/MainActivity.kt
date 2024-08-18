package com.example.todolist



import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AlertDialog.*


import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.DataBase.Task
import com.example.todolist.DataBase.TaskViewModel
import com.example.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), FragmentAddToDo.OnTimePickerClickListener,TimePickerFragment.TimePickerOnSetListener
,FragmentAddToDo.DatePickerOnClickListener
,DatePickerFragment.OnDataPickerClickSettedListener
,FragmentAddToDo.AddItemClickListener{
    private lateinit var binding:ActivityMainBinding


    //private var taskViewModel:TaskViewModel? =Application.taskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)









        setBackGroundForBottomNavMenu()


        bottomNavMenuListener()


        floatingActionButtonListener()




    }



    private fun floatingActionButtonListener() {
        binding.fabMain.setOnClickListener{
            FragmentAddToDo()
                .show(supportFragmentManager,"AddNewTask")

        }
    }

    private fun bottomNavMenuListener() {
        binding.bottomNavBarBottomMenu.setOnNavigationItemSelectedListener {

            val fragment:Fragment= when(it.itemId)
            {
                R.id.miMenu -> FragmentToDoList()
                R.id.miSettings -> FragmentSettings()
                else -> throw IllegalArgumentException("your choice is not right")
            }
            pushFragment(fragment)
            true


        }
        binding.bottomNavBarBottomMenu.selectedItemId = R.id.miMenu

    }

    private fun setBackGroundForBottomNavMenu() {
        binding.bottomNavBarBottomMenu.background= null

    }

    private fun pushFragment(fragment: Fragment) {
        if(fragment is FragmentToDoList)
        {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id,fragment)
                .addToBackStack("ListFragment")
                .commit()
        }
        else
        {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id,fragment)
                .commit()
        }


    }












    override fun onResume() {
        super.onResume()
        binding.bottomNavBarBottomMenu.selectedItemId = R.id.miMenu
    }

    override fun onTimePickerClick() {
        TimePickerFragment().show(supportFragmentManager,null)
    }

    override fun onTimePickerSettedListener(hourOfDay:Int,minute:Int, AM_PM: String) {
        var minutes_string:String=minute.toString()
        if(minute<10)
        {
            minutes_string=""
            minutes_string= "0${minute}"
        }
        val fragmentAddToDO:FragmentAddToDo =supportFragmentManager.findFragmentByTag("AddNewTask") as FragmentAddToDo
        if(hourOfDay >12) {
            fragmentAddToDO.binding.tvTime.text = "${hourOfDay - 12} : ${minutes_string}  ${AM_PM}"
        return
        }
        else
        {
            fragmentAddToDO.binding.tvTime.text = "${hourOfDay} : ${minutes_string}  ${AM_PM}"
        }

    }

    override fun datePickerOnClick() {
        DatePickerFragment().show(supportFragmentManager,null)
    }

    override fun onDataPickerClickSetted(year: Int, month: Int, dayOfMonth: Int) {
        val fragmentAddToDO:FragmentAddToDo =supportFragmentManager.findFragmentByTag("AddNewTask") as FragmentAddToDo

        fragmentAddToDO.binding.tvDate.text= "${dayOfMonth}/${month+1}/${year}"
    }

    override fun addItemClick(task: Task) {

       FragmentToDoList.isAddTask=true


    }


}