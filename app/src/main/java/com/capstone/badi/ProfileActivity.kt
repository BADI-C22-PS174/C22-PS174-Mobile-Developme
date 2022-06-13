package com.capstone.badi

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.badi.databinding.ActivityProfileBinding
import com.capstone.badi.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var selectedImg: Uri
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //config progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("Updating Profile..")
        progressDialog.setCanceledOnTouchOutside(false)
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        binding.imgUser.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        binding.btnContinue.setOnClickListener {
            if (binding.myEditTextName.text!!.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }else if (binding.myEditTextNameShop.text!!.isEmpty()) {
                Toast.makeText(this, "Please enter your name shop", Toast.LENGTH_SHORT).show()
            }else if (selectedImg == null) {
                Toast.makeText(this, "Please select your image ", Toast.LENGTH_SHORT).show()
            }else uploadData()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                selectedImg = data.data!!
                binding.imgUser.setImageURI(selectedImg)
            }
        }
    }

    private fun uploadData() {
        val baos = ByteArrayOutputStream()
        val reference = FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.email}")
//        val reference = storage.reference.child("profile").child(Date().time.toString())
        reference.putFile((selectedImg)).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Uploading data, Please wait... ", Toast.LENGTH_SHORT).show()
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(imgUrl: String) {
        val user = UserModel( binding.myEditTextName.text.toString(),
            binding.myEditTextNameShop.text.toString(),
            imgUrl
        )
        progressDialog.show()
        database.reference.child("users").child(firebaseAuth.currentUser?.uid.toString()).setValue(user)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, WelcomeActivity::class.java))
                Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "insert data failed", Toast.LENGTH_SHORT).show()
            }
    }
}

