package com.capstone.badi.ui.account

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
import com.capstone.badi.ProfileActivity
import com.capstone.badi.WelcomeActivity
import com.capstone.badi.databinding.FragmentProfileBinding
import com.capstone.badi.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
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
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.progressBar.visibility = View.VISIBLE

        firebaseAuth = FirebaseAuth.getInstance()
        uid = firebaseAuth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        if (uid.isNotEmpty()){
            getUserData()
        }

        binding.btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            Intent(activity, WelcomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        }
        binding.btnEdit.setOnClickListener {
            Intent(activity, ProfileActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(it)
            }
        }

        return root
    }

    private fun getUserData() {
        databaseReference.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                user = snapshot.getValue(UserModel::class.java)!!
                binding.txtNamaPemilik.setText(user.nama)
                binding.txtNamaToko.setText((user.nama_toko))
                getUserProfile()
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"Failed to get user profile data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getUserProfile() {
        storageReference = FirebaseStorage.getInstance().reference.child("img_user/${FirebaseAuth.getInstance().currentUser?.email}")
        val localFile = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.imgUser.setImageBitmap(bitmap)
            binding.progressBar.visibility = View.GONE
        }.addOnFailureListener{
            Toast.makeText(activity,"Failed to retrieve image", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}