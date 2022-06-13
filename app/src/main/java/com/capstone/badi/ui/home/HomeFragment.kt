package com.capstone.badi.ui.home

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.badi.databinding.FragmentHomeBinding
import com.capstone.badi.menuHome.analysis.AnalysisActivity
import com.capstone.badi.menuHome.myNotes.ui.main.MyNotesActivity
import com.capstone.badi.menuHome.myProduct.ui.main.MyProductActivity
import com.capstone.badi.menuHome.transaction.TransactionActivity
import com.capstone.badi.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var user: UserModel
    private lateinit var uid: String
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        firebaseAuth = FirebaseAuth.getInstance()
        uid = firebaseAuth.currentUser?.uid.toString()
        databaseReference =FirebaseDatabase.getInstance().getReference("users")
        if (uid.isNotEmpty()){
            getUserData()
        }
        binding.viewAnalysis.setOnClickListener {
            val intent = Intent(activity, AnalysisActivity::class.java)
            startActivity(intent)
        }

        binding.viewMyNotes.setOnClickListener {
            val intent = Intent(activity, MyNotesActivity::class.java)
            startActivity(intent)
        }
        binding.viewMyProduct.setOnClickListener {
            val intent = Intent(activity, MyProductActivity::class.java)
            startActivity(intent)
        }
        binding.viewMyTransaction.setOnClickListener {
            val intent = Intent(activity, TransactionActivity::class.java)
            startActivity(intent)
        }

        val textView: TextView = binding.tvItemToko

        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it

        }
        binding.progressBar.visibility = View.VISIBLE
        return root


    }


    private fun getUserData() {
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                user = snapshot.getValue(UserModel::class.java)!!
                binding.tvItemUsername.setText(user.nama)
                binding.tvItemToko.setText((user.nama_toko))
                getUserProfile()
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"Failed to get user profile data",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUserProfile() {
        storageReference =FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.email}")
        val localFile = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.imgItemPhoto.setImageBitmap(bitmap)
            binding.progressBar.visibility = View.GONE
        }.addOnFailureListener{
            Toast.makeText(activity,"Failed to retrieve image",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}