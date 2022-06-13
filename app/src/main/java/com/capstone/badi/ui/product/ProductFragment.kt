package com.capstone.badi.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.badi.R
import com.capstone.badi.adapter.ListProductAdapter
import com.capstone.badi.databinding.FragmentProductBinding
import com.capstone.badi.menuHome.analysis.AnalysisActivity
import com.capstone.badi.menuHome.myProduct.ui.main.MyProductActivity
import com.capstone.badi.model.CategoryModel
import com.capstone.badi.model.ProductModel
import java.util.ArrayList

class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val arrayList = ArrayList<CategoryModel>()
        arrayList.add(CategoryModel("Stationary","Buku, Tas, Pensil, Dll", R.drawable.alat_tullis))
        arrayList.add(CategoryModel("Rice","Beras Murah, Beras Merah, Beras Mahal, Dll", R.drawable.beras))
        arrayList.add(CategoryModel("Bulks","Bawang Putih, Kacang Tanah, Jagung, Telor, Dll", R.drawable.curah))
        arrayList.add(CategoryModel("Necessities","Buku, Tas, Pensil, Dll", R.drawable.keperluan_dapur))
        arrayList.add(CategoryModel("Crackle Bag","Dayak, sendok, loco, Dll", R.drawable.kantong_kresek))
        arrayList.add(CategoryModel("Soap", "Sabun Mandi, Sabun Cuci, Sabun Wajah,  Dll", R.drawable.sabun))
        arrayList.add(CategoryModel("Snacks","Ciki, Permen, Biskuit Dll", R.drawable.jajanan))
        arrayList.add(CategoryModel("Coffee","Kopi Hitam, Kopi Susu, Dll", R.drawable.kopi))
        arrayList.add(CategoryModel("LPG","LPG 3 kg, LPG 12kg, Dll", R.drawable.gas_elpiji))
        arrayList.add(CategoryModel("Flip-Flops","Sandal Swallow, Sandal Selop, Dll", R.drawable.kantong_kresek))
        arrayList.add(CategoryModel("Mie","Mie Goreng, Mie Kuah , Dll", R.drawable.mie_instan))
        arrayList.add(CategoryModel("Drink","Minuman Gelas, Minuman Botol, Dll", R.drawable.minuman))
        arrayList.add(CategoryModel("Drug","Obat Sakit Kepala, Obat Sakit Gigi, Dll", R.drawable.kantong_kresek))
        arrayList.add(CategoryModel("Plastic","Plastik Tomat, Dll", R.drawable.kantong_kresek))
        arrayList.add(CategoryModel("Home Supply","Sapu, Sapu Ijuk , Dll", R.drawable.perlengkapan_rumah_tangga))
        arrayList.add(CategoryModel("Milk","Susu Putih, Susu Coklat , Dll", R.drawable.susu))
        arrayList.add(CategoryModel("Tea","Thai Tea,Teh celup, Teh Botol, Dll", R.drawable.teh))
        arrayList.add(CategoryModel("Electric Pulse","Pulsa Hp, Token Listrik, Dll", R.drawable.pulsa_elektrik))

        val listProduct = ListProductAdapter(arrayList,activity)
        binding.rvCategory.layoutManager = LinearLayoutManager(activity)
        binding.rvCategory.adapter = listProduct

        binding.textView.setOnClickListener{
            val intent = Intent(activity, MyProductActivity::class.java)
            startActivity(intent)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}