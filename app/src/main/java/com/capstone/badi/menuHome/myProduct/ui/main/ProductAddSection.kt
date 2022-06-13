package com.capstone.badi.menuHome.myProduct.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.capstone.badi.R
import com.capstone.badi.databinding.ActivityProductAddUpdateBinding
import com.capstone.badi.menuHome.myProduct.helper.customProgressBar
import com.capstone.badi.menuHome.myProduct.helper.snackbar
import com.capstone.badi.model.ProductModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.skydoves.powerspinner.SpinnerGravity

class ProductAddSection : AppCompatActivity() {
    private val category=ArrayList<String>()
    private lateinit var selectedImg: Uri
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var uid: String
    private lateinit var databaseReference: DatabaseReference


    private lateinit var binding: ActivityProductAddUpdateBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Add Products"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        storage = FirebaseStorage.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("Updating Profile..")
        progressDialog.setCanceledOnTouchOutside(false)
        database = FirebaseDatabase.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        uid = firebaseAuth.currentUser?.uid.toString()
            category.add("Fashion")
            category.add("Electronics")
            category.add("Groceries")
            category.add("Furniture")
            category.add("Sports")
            category.add("Beauty")
            category.add("Food")
            category.add("Others")

            binding.categoryDropdownList.arrowAnimate = true
            binding.categoryDropdownList.setItems(category)
            binding.categoryDropdownList.arrowGravity = SpinnerGravity.END
            // category_dropdownList.selectItemByIndex(0)
            binding.categoryDropdownList.lifecycleOwner = this@ProductAddSection


        binding.addImagesBtn.setOnClickListener {
                if (!askForPermissions()) {
                    return@setOnClickListener
                }
                openGalleryForImages()
            }

        binding.addProductSubmit.setOnClickListener{
            if(!validateProductName() or !validateProductPrice() or !validateProductStock() or !validateProductCategory() or !validateProductDescription()){
                return@setOnClickListener
            }
            binding.addProductSubmit.isEnabled=false
            val index=binding.categoryDropdownList.selectedIndex
            val dialog= customProgressBar(this)
            uploadFile(dialog)

        }
        binding.addPDescription.setOnTouchListener(OnTouchListener { v, event ->
            if (v.id == R.id.add_p_description) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
                    else->{
                        performClick()
                    }
                }
            }
            return@OnTouchListener false
        })

    }

    private fun uploadFile(dialog: AlertDialog) {
        val reference = FirebaseStorage.getInstance().reference.child("user/img_product/${FirebaseAuth.getInstance().currentUser?.email}")
        reference.putFile((selectedImg)).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Uploading data, Please wait... ", Toast.LENGTH_SHORT).show()
                reference.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())
                }
            }
        }
        dialog.show()
    }

    private fun uploadInfo(imgUrl: String) {
        val user = ProductModel( imgUrl,binding.addPMarketPrice.text.toString(),binding.addPName.text.toString(),
            binding.categoryDropdownList.text.toString(),binding.addPPrice.text.toString(),
            binding.addPStock.text.toString(),binding.addPDescription.text.toString()

        )
        progressDialog.show()
        database.reference.child("product")
            .child(uid)
            .setValue(user)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MyProductActivity::class.java))
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendNotification(binding.addPName.toString(),"Your Product has been successfully uploaded.")
                }
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "insert data failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun performClick() {
        binding.addPDescription.isEnabled=true
    }

    private fun openGalleryForImages() {

        // For latest versions API LEVEL 19+
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                selectedImg = data.data!!
                binding.imageSlider.setImageURI(selectedImg)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home->{
                super.onBackPressed()
                true
            }
            else -> {
                return true
            }
        }
    }



    //permission area
    private fun isPermissionsAllowed(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
    private fun askForPermissions(): Boolean {
        if (!isPermissionsAllowed()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as Activity,Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this as Activity,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),200)
            }
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            200 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   print("success") // permission is granted, you can perform your operation here
                }
                return
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton("App Settings"
            ) { _, _ ->
                // send to app settings if permission is denied permanently
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel",null)
            .show()
    }

    //validation area
    private fun validateProductName():Boolean{
        val name=binding.addPName.text.toString().trim()
        return when{
            name.isEmpty()->{
                binding.addPName.error="Field cannot be empty."
                false
            }
            name.length>26->{
                binding.addPName.error="Try providing a short name."
                return false
            }

            else->{
                binding.addPName.error=null
                return true
            }
        }
    }
    private fun validateProductPrice():Boolean{
        val price=binding.addPPrice.text.toString().trim()
        val marketPrice=binding.addPMarketPrice.text.toString().trim()

        return when{
            price.isEmpty()->{
                binding.addPPrice.error="Field cannot be empty."
                false
            }
            (price.toInt()>200000)->{
                binding.addPPrice.error="Cannot trade this much valuable products."
                return false
            }
            marketPrice.isEmpty()->{
                binding.addPMarketPrice.error="Field cannot be empty."
                false
            }
            (marketPrice.toInt()>200000)->{
                binding.addPMarketPrice.error="Cannot trade this much valuable products."
                return false
            }
            else->{
                binding.addPPrice.error=null
                binding.addPMarketPrice.error=null
                return true
            }
        }
    }
    private fun validateProductStock():Boolean{
        val stock=binding.addPStock.text.toString().trim()
        return when{
            stock.isEmpty()->{
                binding.addPStock.error="Field cannot be empty."
                false
            }
            else->{
                binding.addPStock.error=null
                return true
            }
        }
    }
    private fun validateProductDescription():Boolean{
        val desc=binding.addPDescription.text.toString().trim()
        return when{
            desc.isEmpty()->{
                binding.addPDescription.error="Field cannot be empty."
                false
            }
            desc.length>250->{
                binding.addPDescription.error="Limit  250 characters"
                return false
            }
            else->{
                binding.addPDescription.error=null
                return true
            }
        }
    }
    private fun validateProductCategory():Boolean{
        val range=0..6
        val cat= binding.categoryDropdownList.selectedIndex
        return when{
            (range.contains(cat))->{
                true
            }
            else->{
                binding.layoutRoot.snackbar("Choose the Product Category !")
                return false
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNotification(title: String,msg:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent=Intent(this,MyProductActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val openActivity= PendingIntent.getActivity(this,10,intent,0)
            val customView = RemoteViews(packageName, R.layout.notification_added_to_cart)
            customView.setTextViewText(R.id.notify_cart_title,title)
            customView.setTextViewText(R.id.notify_cart_desc,msg)
            customView.setOnClickFillInIntent(R.id.notify_cart_view_btn,intent)
            val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "cart")
                .setSmallIcon(R.drawable.ic_outline_shopping_cart_24)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setContent(customView)
                .setContentIntent(openActivity)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            val manager = NotificationManagerCompat.from(this)
            manager.notify(0, builder.build())
        }
    }
}