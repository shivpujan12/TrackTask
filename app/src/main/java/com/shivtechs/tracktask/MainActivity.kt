package com.shivtechs.tracktask

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.RelativeLayout.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shivtechs.tracktask.activityFragments.Friends
import com.shivtechs.tracktask.activityFragments.MyDay
import com.shivtechs.tracktask.activityFragments.Projects
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.profile_layout.*


class MainActivity : AppCompatActivity() {
    var flagTopBar: Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //disable the dark mode
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        loadFragment(MyDay())

        focusBtn.setOnClickListener {
            startActivity(Intent(this,FocusActivity::class.java))
        }

        signOutBtn.setOnClickListener{
            Firebase.auth.signOut()
            startActivity(Intent(this,AuthActivity::class.java))
            finish()
        }

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.projects -> {
                    switchTopBar()
                    subtext.text = "Projects"
                    loadFragment(Projects())
                }
                R.id.myDay -> {
                    switchTopBar()
                    subtext.text = "Thursday, 12 January"
                    loadFragment(MyDay())
                }
                R.id.friends -> {
                    switchTopBar()
                    subtext.text = "Friends"
                    loadFragment(Friends())
                }
                R.id.settings -> {
                    //show profile layout

                    val scaleAnimation = AnimationUtils.loadAnimation(this,R.anim.scale_up_view_match_parent)
                    val alphaAnimation = AnimationUtils.loadAnimation(this,R.anim.enable_view_animation)
                    scaleAnimation.setAnimationListener(object : AnimationListener {
                        override fun onAnimationStart(animation: Animation) {
                            topBar.layoutParams = LayoutParams(topBar.width, LayoutParams.MATCH_PARENT)
                            layout1.visibility = View.GONE
                        }
                        override fun onAnimationEnd(animation: Animation) {
                            topBar.layoutParams = LayoutParams(topBar.width, LayoutParams.MATCH_PARENT)
                            profileLayout.startAnimation(alphaAnimation)
                        }

                        override fun onAnimationRepeat(animation: Animation) {}
                    })
                    alphaAnimation.setAnimationListener(object: AnimationListener{
                        override fun onAnimationStart(p0: Animation?) {
                            profileLayout.visibility = View.VISIBLE
                            profileLayout.alpha = 1f
                        }

                        override fun onAnimationEnd(p0: Animation?) {

                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }

                    })
                    topBar.startAnimation(scaleAnimation)
                    flagTopBar = true

                }
            }
            true
        }


    }

    fun switchTopBar(){
        if (flagTopBar) {
            val scaleAnimation = AnimationUtils.loadAnimation(this,R.anim.scale_down_view)
            profileLayout.alpha = 0f
            topBar.startAnimation(scaleAnimation)
            scaleAnimation.setAnimationListener(object :AnimationListener{
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    layout1.visibility = View.VISIBLE
                    topBar.layoutParams = LayoutParams(topBar.width, LayoutParams.WRAP_CONTENT)
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }

            })

            flagTopBar = false
        }
    }


    fun loadFragment(fragment: Fragment) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fragmentContainer, fragment)
        beginTransaction.commit()
    }

    fun changeTopBarView(str: String) {
        subtext.text = "Project Name"

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            subtext.text = "Projects"
        } else {
            finish()
        }
    }


}