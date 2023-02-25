package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.data.PopularProductResponse
import com.redeyesncode.andromerce.databinding.AllProductItemBinding

class AllProductAdapter(var context: Context,var popularProductResponse: PopularProductResponse,var onEventActivity:onEvent):RecyclerView.Adapter<AllProductAdapter.AllProductViewHolder>() {

    lateinit var binding: AllProductItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllProductViewHolder {

        binding = AllProductItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return AllProductViewHolder(binding)

    }

    override fun onBindViewHolder(holder: AllProductViewHolder, position: Int) {

        val data =  popularProductResponse.data.get(position)

        holder.binding.tvProductName.text =data.productTable?.productName
        holder.binding.tvProductPrice.text = "Price : ${data.productTable?.sellingPrice}"
        holder.binding.tvCategoryName.text = "Category : ${data.category?.name}"
        Glide.with(context).load(data.media.get(0).link).placeholder(R.drawable.ic_placeholder).into(holder.binding.ivProductImage)

        holder.binding.mainLayout.setOnClickListener {
            onEventActivity.onProductClick(position,data.productTable?.id.toString())
        }


    }

    override fun getItemCount(): Int {
        return popularProductResponse.data.size

    }

    public interface onEvent{
        fun onProductClick(position: Int,productId:String)

    }

    class AllProductViewHolder(var binding:AllProductItemBinding):RecyclerView.ViewHolder(binding.root)
}