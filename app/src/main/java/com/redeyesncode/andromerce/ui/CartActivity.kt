package com.redeyesncode.andromerce.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.redeyesncode.andromerce.base.BaseActivity
import com.redeyesncode.andromerce.data.CartResponseData
import com.redeyesncode.andromerce.databinding.ActivityCartBinding
import com.redeyesncode.andromerce.presentation.CartViewModel
import com.redeyesncode.andromerce.ui.adapters.CartAdapter
import com.redeyesncode.andromerce.utils.AppSession
import com.redeyesncode.andromerce.utils.Constant

class CartActivity : BaseActivity(),CartAdapter.onEvent {
    private lateinit var binding:ActivityCartBinding
    private lateinit var viewModel:CartViewModel
    private lateinit var adapter :CartAdapter
    private var dataList:ArrayList<CartResponseData.Data> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        initClicks()
        setupViewModel()
        attachObservers()
        adapter = CartAdapter(this,dataList,this)
        intialApiCall()
        setContentView(binding.root)
    }

    override fun onDeleteClick(position: Int, cartId: Int) {

        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Delete Item ?")
        //set message for alert dialog
        builder.setMessage("Are you sure you want to delete this item ?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->
            showLoader()
            val map = HashMap<String,String>()
            map.put("cart_id",cartId.toString())
            viewModel.deleteCart(map)
        }

        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->

        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()



    }

    private fun intialApiCall() {
        showLoader()
        val map = HashMap<String,String>()
        map.put("user_id",AppSession(this@CartActivity).getUser()?.id.toString())
        viewModel.getCart(map)


    }

    private fun attachObservers() {

        viewModel.isFailed.observe((this)){
            hideLoader()
            if(it!=null){
                showToast(it)
            }
        }
        viewModel.isSuccess.observe((this)){
            if(it){
                showLoader()
            }else{
                hideLoader()
            }
        }
        viewModel.cartResponse.observe((this)){
            hideLoader()
            if(it!=null){

                setAdapter(it.data)

            }else{
                showToast(Constant.OOPS_SW)
            }



        }
        viewModel.commonResponse.observe((this)){
            hideLoader()
            showToast(it.message.toString())
            val map = HashMap<String,String>()
            map.put("user_id",AppSession(this@CartActivity).getUser()?.id.toString())
            viewModel.getCart(map)
        }


    }

    private fun setAdapter(data: ArrayList<CartResponseData.Data>) {
        dataList = data
        adapter = CartAdapter(this,dataList,this)
        binding.recvCart.adapter = adapter
        binding.recvCart.layoutManager = LinearLayoutManager(this@CartActivity,LinearLayoutManager.VERTICAL,false)

    }

    private fun setupViewModel() {
        viewModel = CartViewModel()
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        binding.viewmodel = viewModel


    }

    private fun initClicks() {
        binding.btnAddToCart.setOnClickListener {
            // Need to send array of product id to place order api now....

            val orderPlaceintent = Intent(this@CartActivity,CartAddressActivity::class.java)
            val productIds :ArrayList<Int> = arrayListOf()
            for (data in dataList){
                productIds.add(data.productId!!.toInt())

            }

            orderPlaceintent.putIntegerArrayListExtra("PRODUCT_IDS",productIds)

            startActivity(orderPlaceintent)

        }

        binding.commonTitleBar.tvTitle.text = "Cart"
        binding.commonTitleBar.backIcon.setOnClickListener {

            finish()
        }

    }
}