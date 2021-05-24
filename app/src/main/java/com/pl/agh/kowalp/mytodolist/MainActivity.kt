package com.pl.agh.kowalp.mytodolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ListSelectionRecyclerViewClickListener {


    lateinit var listRecyclerView: RecyclerView

    val listDataManager: ListDataManager = ListDataManager(this)

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


        val lists = listDataManager.readList()


        findViewById<FloatingActionButton>(R.id.add_list_button).setOnClickListener { view ->
            showCreateListDialog()
        }

        listRecyclerView = findViewById(R.id.lists_recycler_view)
        listRecyclerView.layoutManager = LinearLayoutManager(this)

        listRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this )
    }

    private fun showListDetails(list: TaskList) {
        val listDetailIntent = Intent(this, ListDetailsActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        startActivityForResult(listDetailIntent, LIST_DETAIL_REQUEST_CODE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == LIST_DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                listDataManager.saveList(data.getParcelableExtra(INTENT_LIST_KEY))
                updateLists()
            }
        }
    }

    private fun updateLists() {
        val lists = listDataManager.readList()
        listRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateListDialog() {
        val dialogTitle: String = getString(R.string.list_name)
        val positiveButtonTitle = getString(R.string.create_list)

        val alertDialogBuilder = AlertDialog.Builder(this)
        val listTitleEditionText = EditText(this)
        listTitleEditionText.inputType = InputType.TYPE_CLASS_TEXT

        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder.setView(listTitleEditionText)

        alertDialogBuilder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val list = TaskList(listTitleEditionText.text.toString())
            listDataManager.saveList(list)
            val recyclerViewAdapter = listRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerViewAdapter.addList(list)
            showListDetails(list)
        }

        alertDialogBuilder.create().show()


    }

    override fun listItemClicked(list: TaskList) {
        showListDetails(list)
    }
}