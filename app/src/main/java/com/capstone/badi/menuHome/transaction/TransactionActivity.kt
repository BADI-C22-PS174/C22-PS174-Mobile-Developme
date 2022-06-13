package com.capstone.badi.menuHome.transaction

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.badi.databinding.ActivityTransactionBinding
import com.capstone.badi.menuHome.analysis.AnalysisActivity
import com.capstone.badi.menuHome.transaction.helper.FunctionHelper
import com.capstone.badi.ui.history.HistoryFragment

class TransactionActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTransactionBinding
    var hargaBuku = 12000
    var itemCount = 0
    var countBuku = 0
    var totalItems = 0
    var totalPrice = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater);
        setContentView(binding.root)

//        setDataBuku()
//        setTotalPrice()

        binding.btnCheckout.setOnClickListener {
            val intent = Intent(this, HistoryFragment::class.java)
            startActivity(intent)
        }
    }

//    private fun setDataBuku() {
//        binding.imgAdd.setOnClickListener {
//            itemCount = +1
//            binding.tvNumber.setText(itemCount)
//            countBuku = hargaBuku * itemCount
//            setTotalPrice()
//        }
//
//        binding.imgMinus.setOnClickListener {
//            if (itemCount > 0) {
//                itemCount -= 1
//                binding.tvNumber.setText(itemCount)
//            }
//            countBuku = hargaBuku * itemCount
//            setTotalPrice()
//        }
//    }
//
//    private fun setTotalPrice() {
//        totalItems = itemCount
//        totalPrice = countBuku
//        binding.tvJumlahBarang.text = "$totalItems items"
//        binding.tvTotalPrice.text = FunctionHelper.rupiahFormat(totalPrice)
//    }
//
//    private fun setInputData() {
//        binding.btnCheckout.setOnClickListener {
//            if (totalItems == 0 || totalPrice == 0) {
//                Toast.makeText(
//                    this,
//                    "Harap Masukkan Jumlah Barang!", Toast.LENGTH_SHORT
//                )
//                    .show()
//            } else {
////                addDataViewModel.addData(totalItems, totalPrice)
//                Toast.makeText(
//                    this,
//                    "Penjualan Sukses!!! Silahkan Cek di Menu ModelProduct", Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//        }
//    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}