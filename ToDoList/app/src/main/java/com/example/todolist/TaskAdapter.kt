package com.example.todolist

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.DataBase.Task
import com.example.todolist.databinding.ItemBinding

class TaskAdapter(val tasks:MutableList<com.example.todolist.DataBase.Task>): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    lateinit var binding: ItemBinding
    var onItemClick:ItemOnClickListener?=null


    inner class TaskViewHolder(val binding:ItemBinding): RecyclerView.ViewHolder(binding.root)
    {


        @SuppressLint("ResourceAsColor")
        fun bind(task:Task,position:Int)
        {
            Log.e("task status",task.status)

            binding.root.setOnClickListener {
                Log.e("in adapter position",position.toString())
                onItemClick?.onItemClick(task,position)

            }

            binding.apply {
                tvPlay.text = task.title
                tvTime.text = task.time



                pendingDesignOfItem()


                /* binding For First time if task is done then put the design of Done Task*/
                if(task.status == "Done")
                {
                    Log.e("status","done")
                    doneDesignOfItem()
                }

                cvCheckBtn.setOnClickListener()
                {
                    Log.e("in click","ok")
                    //update in database status = done and update design
                    if(task.status == "Pending")
                    {
                        Log.e("in pending","ok")
                        Application.taskViewModel?.updateTaskStatus(task.id)
                        task.status= "Done"
                        doneDesignOfItem()

                    }



                }

            }



        }

        private fun doneDesignOfItem() {
            binding.apply {
                tvPlay.setTextColor(Color.GREEN)
                viewLine.setBackgroundColor(Color.GREEN)
                tvCheckStatus.text = "Done!"
                tvCheckStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
                tvCheckStatus.setTextColor(Color.GREEN)

                cvCheckBtn.setBackgroundColor(Color.TRANSPARENT)
            }
        }

        private fun pendingDesignOfItem() {
            binding.apply {
                tvPlay.setTextColor(Color.parseColor("#2196F3"))
                viewLine.setBackgroundColor(Color.parseColor("#2196F3"))
                tvCheckStatus.text = ""
                tvCheckStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check,0,0,0)
                cvCheckBtn.setBackgroundColor(Color.BLUE)
                cvCheckBtn.useCompatPadding= true
                cvCheckBtn.setContentPadding(20,20,20,20)
                cvCheckBtn.cardElevation = 5F


            }
        }
    }

     fun addTask(task:Task)
    {
        tasks.add(task)
        notifyItemInserted(tasks.size-1)
    }
    fun removeTask(index:Int)
    {
        tasks.removeAt(index)
        notifyItemRemoved(index)

    }

    fun updateTask(task:Task,position:Int)
    {
        tasks[position] = task
        notifyItemChanged(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder( ItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position],position)


    }

    override fun getItemCount(): Int =  tasks.size


    interface ItemOnClickListener{
        fun onItemClick(task:Task,position:Int)
    }


}

