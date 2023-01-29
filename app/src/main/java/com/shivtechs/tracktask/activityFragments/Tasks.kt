package com.shivtechs.tracktask.activityFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivtechs.tracktask.R
import kotlinx.android.synthetic.main.fragment_tasks.view.*
import kotlinx.android.synthetic.main.item_list.view.*

class Tasks : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tasks, container, false)
        view.taskList.layoutManager = LinearLayoutManager(context)
        view.taskList.adapter = TaskListAdapter(activity)
        return view
    }

    class TaskListAdapter(val activity: FragmentActivity?) : RecyclerView.Adapter<TaskListAdapter.myViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            return myViewHolder(view)
        }

        override fun onBindViewHolder(holder: myViewHolder, position: Int) {
            holder.itemView.eachTask.setOnClickListener{
                Toast.makeText(activity?.applicationContext, "Hello", Toast.LENGTH_SHORT).show()
            }
        }
        override fun getItemCount(): Int {
            return 4
        }

        inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }
}