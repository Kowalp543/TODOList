package com.pl.agh.kowalp.mytodolist

import android.content.Context
import androidx.preference.PreferenceManager

class ListDataManager(private val context: Context) {
    fun saveList(list: TaskList) {
        val sharePreferences = PreferenceManager. getDefaultSharedPreferences(context).edit()

        sharePreferences.putStringSet(list.name, list.tasks.toHashSet())
        sharePreferences.apply()
    }

    fun readList(): ArrayList<TaskList> {
        val taskLists = ArrayList<TaskList>()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val sharedPreferencesContents = sharedPreferences.all

        for (taskList in sharedPreferencesContents) {
            val itemsHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key, itemsHashSet)

            taskLists.add(list)
        }
        return taskLists
    }
}