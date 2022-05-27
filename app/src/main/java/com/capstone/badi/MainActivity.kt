package com.capstone.badi

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.capstone.badi.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        playAnimation()
        buttonListener()
        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        mainBinding.btnListStory.setOnClickListener {
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            finish()
        }

        mainBinding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            Intent(this, WelcomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        }
    }

    private fun checkUser() {
        //if already logged
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, WelcomeActivity::class.java))
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            finish()
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(mainBinding.imageViewLogo, View.TRANSLATION_X, -38f, 38f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val username =
            ObjectAnimator.ofFloat(mainBinding.usernameTextView, View.ALPHA, 1f).setDuration(500)
        val greetings =
            ObjectAnimator.ofFloat(mainBinding.tvHomeGreetings, View.ALPHA, 1f).setDuration(500)
        val listStory =
            ObjectAnimator.ofFloat(mainBinding.btnListStory, View.ALPHA, 1f).setDuration(500)
        val logout = ObjectAnimator.ofFloat(mainBinding.btnLogout, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(username, greetings, listStory, logout)
            startDelay = 500
        }.start()
    }

    private fun buttonListener() {
        mainBinding.imgViewSetting.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }
}