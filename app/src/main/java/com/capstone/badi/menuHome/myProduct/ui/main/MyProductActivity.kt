package com.capstone.badi.menuHome.myProduct.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.badi.HomeActivity
import com.capstone.badi.databinding.ActivityMyProductBinding
import com.capstone.badi.model.ProductModel
import com.capstone.badi.ui.home.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.core.Constants
import com.google.firebase.storage.StorageReference

class MyProductActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var uid: String
    private lateinit var productList: ArrayList<ProductModel>
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding : ActivityMyProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onStart()
        onStart()
        firebaseAuth = FirebaseAuth.getInstance()
        uid = firebaseAuth.currentUser?.uid.toString()
        productList = arrayListOf<ProductModel>()
        supportActionBar?.title="My Products"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.addProducts.setOnClickListener{
            val intent= Intent(this,ProductAddSection::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding.myProductList.apply {
            layoutManager = LinearLayoutManager(this@MyProductActivity)
        }

        getData()

    }

    private fun getData() {
        databaseReference =FirebaseDatabase.getInstance().getReference("product")
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(
                    this@MyProductActivity,
                    "Failed to get user profile data",
                    Toast.LENGTH_SHORT
                ).show()
                    for (empSnap in snapshot.children) {
                        productList.clear()
                        val plist = snapshot.getValue(ProductModel::class.java)
                        productList.add(plist!!)
                    }
                    val mAdapter = ProductAdapter(productList)
                    binding.myProductList.adapter = mAdapter

                binding.shimmerEffectLayout.stopShimmer()
               binding.shimmerEffectLayout.visibility=View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MyProductActivity,
                    "Failed to get user profile data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                val intent= Intent(this,HomeFragment::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            else -> {
                super.onBackPressed()
                return true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}