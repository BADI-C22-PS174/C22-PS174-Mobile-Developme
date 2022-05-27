package com.capstone.badi.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.badi.AnalysisActivity
import com.capstone.badi.RegisterActivity
import com.capstone.badi.databinding.ActivityHomeBinding
import com.capstone.badi.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

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

        binding.viewAnalysis.setOnClickListener {
            val intent = Intent(activity, AnalysisActivity::class.java)
            startActivity(intent)
        }

        val textView: TextView = binding.tvItemToko

        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it

        }
        return root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}