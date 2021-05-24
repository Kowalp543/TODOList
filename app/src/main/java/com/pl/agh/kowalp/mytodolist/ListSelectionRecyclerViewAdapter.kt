package com.pl.agh.kowalp.mytodolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface ListSelectionRecyclerViewClickListener {
    fun listItemClicked(list: TaskList)
}


class ListSelectionRecyclerViewAdapter(private val lists: ArrayList<TaskList>,
                                       val clickListener: ListSelectionRecyclerViewClickListener):
    RecyclerView.Adapter<ListSelectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_selection_view_holder,
            parent,
            false)

        return ListSelectionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.listPosition.text = (position + 1).toString()
        holder.listTitle.text = lists.get(position).name

        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(lists[position])
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun addList(list: TaskList) {
        lists.add(list)
        notifyItemInserted(lists.size - 1)
    }

}