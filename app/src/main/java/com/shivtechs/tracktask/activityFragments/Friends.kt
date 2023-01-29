package com.shivtechs.tracktask.activityFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shivtechs.tracktask.R
import kotlinx.android.synthetic.main.fragment_friends.*
import kotlinx.android.synthetic.main.fragment_friends.view.*

class Friends : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_friends, container, false)
        view.rankList.layoutManager = LinearLayoutManager(context);
        view.rankList.adapter = RankListAdapter()

        val slideIn = AnimationUtils.loadAnimation(activity, R.anim.slide_left_in)
        val slideOut = AnimationUtils.loadAnimation(activity, R.anim.slide_left_out)

        Thread {
            while (true) {
                try {
                    Thread.sleep(1000*5)
                    activity?.runOnUiThread {
                        if (card1.visibility == View.VISIBLE) {
                            card2.visibility = View.VISIBLE
                            card2.startAnimation(slideIn)
                            card1.visibility = View.GONE
                            card1.startAnimation(slideOut)
                        } else if (card2.visibility == View.VISIBLE) {
                            card1.visibility = View.VISIBLE
                            card1.startAnimation(slideIn)
                            card2.visibility = View.GONE
                            card2.startAnimation(slideOut)
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()

        return view;
    }

    class RankListAdapter : RecyclerView.Adapter<RankListAdapter.RankViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rank_item_list,parent,false)
            return  RankViewHolder(view)
        }

        override fun getItemCount(): Int {
            return 3;
        }

        override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        }

        inner class RankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }

    }
}