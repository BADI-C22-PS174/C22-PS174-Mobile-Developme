package com.capstone.badi


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.badi.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
class RegisterActivity: AppCompatActivity() {

    //viewBinding
    private lateinit var registerBinding: ActivityRegisterBinding
    //viewModel
    private lateinit var progressDialog: ProgressDialog

    //firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(registerBinding.root)

        supportActionBar?.hide()
        //config progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("Creating account in..")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance()

        registerBinding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        //register click
        registerBinding.myRegister.setOnClickListener {
            validateData()

        }
    }

    private fun validateData() {
        email = registerBinding.myEditTextEmail.text.toString().trim()
        password = registerBinding.myEditTextPass.text.toString().trim()
        //validate
        if (email.isEmpty()){
            registerBinding.myEditTextEmail.error = "please enter email"
            registerBinding.myEditTextEmail.requestFocus()
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email
            registerBinding.myEditTextEmail.error = "invalid email format"
            registerBinding.myEditTextEmail.requestFocus()
        }
        else if (TextUtils.isEmpty(password)){
            registerBinding.myEditTextPass.error ="please enter password"
        }
        else if (password.length <6){
            registerBinding.myEditTextPass.error ="password must at least 6 characters long"
        }
        else{
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                //login sukses
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(
                    this,
                    "Account created with email $email",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                finish()
            }
            .addOnFailureListener{e->
                Toast.makeText(
                    this,
                    "Signup Failed due to  ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }


}
