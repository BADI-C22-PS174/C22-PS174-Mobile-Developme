package com.capstone.badi


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.badi.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var loginBinding: ActivityLoginBinding
    //viewModel
    private lateinit var progressDialog: ProgressDialog
    //firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)

        playAnimation()
        supportActionBar?.hide()

        //config progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("logging in..")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()

        loginBinding.myButton.setOnClickListener {
            validateData()
        }
        //register click
        loginBinding.btnRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }

    private fun validateData() {
        email = loginBinding.myEditText1.text.toString().trim()
        password = loginBinding.myEditText2.text.toString().trim()
        //validate
        if (email.isEmpty()){
            loginBinding.myEditText1.error = "please enter email"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email
            loginBinding.myEditText1.error = "invalid email format"
        }
        else if (TextUtils.isEmpty(password)){
            loginBinding.myEditText2.error ="please enter password"
        }
        else if (password.length <6){
            loginBinding.myEditText2.error ="password must at least 6 characters long"
        }
        else{
            //data is validated
            firebaseLogin()
        }
    }
    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                //login sukses
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(
                    this,
                    "LoggedIn as $email",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, MainActivity::class.java))
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                finish()
            }
            .addOnFailureListener{e->
                Toast.makeText(
                    this,
                    "Login Failed due to  ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }






    private fun playAnimation() {
        ObjectAnimator.ofFloat(loginBinding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title =
            ObjectAnimator.ofFloat(loginBinding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val message =
            ObjectAnimator.ofFloat(loginBinding.descTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(loginBinding.myEditText1, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(loginBinding.myEditText2, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(loginBinding.myButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, message, emailEditTextLayout, passwordEditTextLayout, login)
            startDelay = 500
        }.start()
    }

}