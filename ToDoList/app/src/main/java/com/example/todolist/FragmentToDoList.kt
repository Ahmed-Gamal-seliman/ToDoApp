package com.example.todolist

import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.RecyclerListener
import com.example.todolist.DataBase.Task
import com.example.todolist.databinding.FragmentToDoListBinding
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.Calendar

class FragmentToDoList : Fragment() {

    lateinit var binding: FragmentToDoListBinding



    var adapter: TaskAdapter? = null


    val activityLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val intent = result.data
        var task: Task? = null
        var position: Int? = null
        if (intent != null) {
            task = IntentCompat.getParcelableExtra(intent, "task", Task::class.java)
            position = intent.getIntExtra("position", 0)
        }

        if (task != null &&
            (result.resultCode == RESULT_OK)
        ) {
            com.example.todolist.Application.taskViewModel?.updateTask(task)
            if (position != null)
                adapter?.updateTask(task, position)

        }
    }


    companion object {
        var isAddTask: Boolean = false

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentToDoListBinding.inflate(inflater)



        com.example.todolist.Application.taskViewModel?.getAllTasks()?.observe(viewLifecycleOwner)
        { items ->

            if (adapter == null) {

                adapter = TaskAdapter(items)
                binding.rvTasks.adapter = adapter
            } else {

                if (isAddTask) {
                    adapter?.addTask(items.get(items.size - 1)) // Update adapter if new items are added
                    isAddTask = false
                }



            }
            adapter?.onItemClick = object : TaskAdapter.ItemOnClickListener {
                override fun onItemClick(task: Task,position:Int) {
                    if (task.status == "Pending") {
                        Log.e("in callback position",position.toString())
                        val intent: Intent = Intent(context, EditTask::class.java)
                        intent.putExtra("realTask", task)
                        intent.putExtra("position",position)
                        activityLauncher.launch(intent)
                    }
                }
            }

        }


        val helper: ItemTouchHelper = ItemTouchHelper(callBack)
        helper.attachToRecyclerView(binding.rvTasks)




        return binding.root
    }


    val callBack: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                com.example.todolist.Application.taskViewModel?.deleteTask(
                    adapter?.tasks?.get(
                        viewHolder.adapterPosition
                    )?.id
                )
                adapter?.removeTask(viewHolder.adapterPosition)

            }


            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )


                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.delete_bg_color
                        )
                    )
                    .addActionIcon(R.drawable.ic_delete)
                    .setSwipeRightLabelColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                    .setSwipeRightLabelTypeface(Typeface.DEFAULT_BOLD)
                    .addSwipeRightLabel("Delete")
                    .create()
                    .decorate()


            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}