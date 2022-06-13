package com.capstone.badi

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.badi.databinding.ActivityMainBinding
import com.capstone.badi.menuHome.myProduct.ui.main.ProductAddSection
import com.capstone.badi.model.UserModel
import com.capstone.badi.ui.onboarding.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var user: UserModel
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        playAnimation()
        buttonListener()
        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        setupView()


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
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }


    private fun checkUser() {
        //if already logged
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this, OnBoardingActivity::class.java))
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