package com.shivtechs.tracktask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.shivtechs.tracktask.Data.User
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    var pageToShow: String = "Login";

    private lateinit var auth: FirebaseAuth;

    val database = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //disable the dark mode
        supportActionBar?.hide()
        setContentView(R.layout.activity_auth)

        auth = Firebase.auth

        val slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_left_in)
        val slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_left_out)


        regisBtn.setOnClickListener {

            //create new user
            val user = User(
                emailR.editText?.text?.toString()?.split("@")?.get(0),
                emailR.editText?.text.toString(),
                passwordR.editText?.text.toString()
            )

            auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("CreateUSER", "createUserWithEmail:success")
                        val currUser = auth.currentUser
                        database.child("users").child(user.userName.toString()).setValue(user)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "your username: " + user.userName,
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this, MainActivity::class.java))
                            }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("CreateUSER", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


        }

        loginBtn.setOnClickListener {
            val userCheck = User(
                email.editText?.text?.toString()?.split("@")?.get(0),
                email.editText?.text.toString(),
                password.editText?.text.toString()
            )

            auth.signInWithEmailAndPassword(userCheck.email, userCheck.password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("SIGN IN", "signInWithEmail:success")
                        val currentUser = auth.currentUser
                        database.child("users").child(userCheck.userName.toString()).get()
                            .addOnSuccessListener {
                                if (it.child("password").value == userCheck.password) {
                                    Toast.makeText(
                                        this,
                                        "welcome " + userCheck.userName.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this, MainActivity::class.java))
                                }
                            }.addOnFailureListener {
                            Toast.makeText(this, "" + it.printStackTrace(), Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SIGN IN", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


        }


        login_bridge.setOnClickListener {
            pageToShow = "Regis";
            //start the animation to switch
            regisPage.visibility = View.VISIBLE;
            regisPage.startAnimation(slideIn);
            loginPage.visibility = View.GONE;
            loginPage.startAnimation(slideOut)

        }

        regis_bridge.setOnClickListener {
            pageToShow = "Login";
            //start the animation to switch
            loginPage.visibility = View.VISIBLE
            loginPage.startAnimation(slideIn);
            regisPage.visibility = View.GONE
            regisPage.startAnimation(slideOut);
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser!=null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}