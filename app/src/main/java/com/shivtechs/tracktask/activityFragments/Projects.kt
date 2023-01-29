package com.shivtechs.tracktask.activityFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivtechs.tracktask.MainActivity
import com.shivtechs.tracktask.R
import kotlinx.android.synthetic.main.fragment_projects.view.*
import kotlinx.android.synthetic.main.project_item_list.view.*

class Projects : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_projects, container, false)
        view.projectsList.layoutManager = LinearLayoutManager(context)
        val projectListAdapter = ProjectListAdapter(activity)
        view.projectsList.adapter = projectListAdapter
        return view
    }

    class ProjectListAdapter(val activity: FragmentActivity?) : RecyclerView.Adapter<ProjectListAdapter.myViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.project_item_list, parent, false)
            return myViewHolder(view)
        }

        override fun onBindViewHolder(holder: myViewHolder, position: Int) {
            holder.itemView.eachProject.setOnClickListener{
                (activity as MainActivity).changeTopBarView("Project Name")
                val beginTrans = activity?.supportFragmentManager?.beginTransaction()
                beginTrans?.replace(R.id.fragmentContainer,Tasks())
                beginTrans?.addToBackStack("dummy")
                beginTrans?.commit()
            }
        }
        override fun getItemCount(): Int {
            return 3
        }

        inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

}