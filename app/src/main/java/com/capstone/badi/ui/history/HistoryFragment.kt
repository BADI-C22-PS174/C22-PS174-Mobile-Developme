package com.capstone.badi.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.badi.adapter.HistoryAdapter
import com.capstone.badi.databinding.FragmentHistoryBinding
import com.capstone.badi.model.HistoryModel
import java.util.ArrayList

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val arrayList = ArrayList<HistoryModel>()
        arrayList.add(HistoryModel("5 April 2022","11","Rp. 110.000", "Lunas"))
        arrayList.add(HistoryModel("5 Apri; 2022","12","Rp. 120.000", "Lunas"))
        arrayList.add(HistoryModel("5 April 2022","14","Rp. 140.000", "Lunas"))
        arrayList.add(HistoryModel("5 April 2022","11","Rp. 110.000", "Lunas"))
        arrayList.add(HistoryModel("5 Apri; 2022","10","Rp. 120.000", "Lunas"))
        arrayList.add(HistoryModel("5 April 2022","9","Rp. 140.000", "Lunas"))
        arrayList.add(HistoryModel("6 April 2022","1","Rp. 10.000", "Lunas"))


        val listProduct = HistoryAdapter(arrayList,activity)
        binding.rvHistory.layoutManager = LinearLayoutManager(activity)
        binding.rvHistory.adapter = listProduct
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}