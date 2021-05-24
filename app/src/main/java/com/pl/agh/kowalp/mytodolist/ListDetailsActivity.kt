package com.pl.agh.kowalp.mytodolist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListDetailsActivity : AppCompatActivity() {

    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var addTaskButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_details)

        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)
        title = list.name

        taskListRecyclerView = findViewById(R.id.list_items_recycler_view)
        taskListRecyclerView.adapter = TaskListRecyclerViewAdapter(list)
        taskListRecyclerView.layoutManager = LinearLayoutManager(this)

        addTaskButton = findViewById(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            showCreatTaskDialog()
        }
    }
    fun showCreatTaskDialog() {
        val dialogTitle: String = getString(R.string.task_name_question)
        val positiveButtonTitle = getString(R.string.create_task)

        val alertDialogBuilder = AlertDialog.Builder(this)
        val taskTitleEditionText = EditText(this)
        taskTitleEditionText.inputType = InputType.TYPE_CLASS_TEXT

        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder.setView(taskTitleEditionText )

        alertDialogBuilder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val task = taskTitleEditionText.text.toString()
            list.tasks.add(task)
            val recyclerViewAdapter = taskListRecyclerView.adapter as TaskListRecyclerViewAdapter
            recyclerViewAdapter.notifyItemInserted(list.tasks.size - 1)
            dialog.dismiss()
        }

        alertDialogBuilder.create().show()
    }

    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)

        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}