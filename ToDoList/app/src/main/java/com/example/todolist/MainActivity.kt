package com.example.todolist



import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
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


class MainActivity : AppCompatActivity()
,FragmentAddToDo.AddItemClickListener{
    private lateinit var binding:ActivityMainBinding


    //private var taskViewModel:TaskViewModel? =Application.taskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




    Log.e("onCreate","Yes On create")




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


            Log.e("clicked bottom nav","yes")
            val fragment:Fragment= when(it.itemId)
            {
                R.id.miMenu -> FragmentToDoList()

                R.id.miSettings -> {
                    Log.e("settings","yes")
                    FragmentSettings()
                }
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

            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id,fragment)
                .disallowAddToBackStack()
                .commit()



    }













    override fun onResume() {
        super.onResume()
//        binding.bottomNavBarBottomMenu.selectedItemId = R.id.miMenu
    }







    override fun addItemClick(task: Task) {

       FragmentToDoList.isAddTask=true


    }


}