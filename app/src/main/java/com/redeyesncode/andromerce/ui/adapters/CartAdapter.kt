package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.data.CartResponseData
import com.redeyesncode.andromerce.databinding.CartItemBinding

class CartAdapter(var context: Context,var data:ArrayList<CartResponseData.Data>,var onEventActivity:onEvent):RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    lateinit var binding: CartItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        binding = CartItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return CartViewHolder(binding)


    }

    interface onEvent{
        fun onDeleteClick(position: Int,cartId:Int)

    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = data[position];
        holder.binding.tvProductName.text = product.product?.productName
        holder.binding.tvItemOriginalPrice.text  ="Rs ${product.product?.maxPrice}"
        holder.binding.tvItemDiscountPrice.text= "Rs ${product.product?.sellingPrice}"

        holder.binding.icDelete.setOnClickListener {

            onEventActivity.onDeleteClick(position,product.cartId!!.toInt())

        }
        Glide.with(context).load(product.image).placeholder(R.drawable.ic_placeholder).into(holder.binding.imageProductDetails)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CartViewHolder(var binding:CartItemBinding):RecyclerView.ViewHolder(binding.root)
}