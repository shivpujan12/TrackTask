package com.shivtechs.tracktask.activityFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivtechs.tracktask.R
import com.shivtechs.tracktask.RecyclerAdapter
import kotlinx.android.synthetic.main.fragment_my_day.view.*

class MyDay : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_day, container, false)
        view.list.layoutManager = LinearLayoutManager(context)
        view.list.adapter = RecyclerAdapter()
        return view
    }

}