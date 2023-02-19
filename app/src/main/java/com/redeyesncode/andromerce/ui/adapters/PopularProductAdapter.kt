package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.data.PopularProductResponse
import com.redeyesncode.andromerce.databinding.AllPopularProductsListBinding

class PopularProductAdapter (var context: Context,var popularProductResponse: PopularProductResponse):RecyclerView.Adapter<PopularProductAdapter.MYViewHolder>(){

    lateinit var binding: AllPopularProductsListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYViewHolder {
        binding = AllPopularProductsListBinding.inflate(LayoutInflater.from(context),parent,false)


        return MYViewHolder(binding)


    }

    override fun onBindViewHolder(holder: MYViewHolder, position: Int) {
        val data = popularProductResponse.data.get(position)
        holder.binding.tvPopularProductsName.text = data.productTable?.productName
        holder.binding.tvPricePopularProdect.text = data.productTable?.maxPrice
        holder.binding.tvPricePopularProdect2.text = data.productTable?.sellingPrice


        try {
            Glide.with(context).load(data.media.get(0).link).placeholder(R.drawable.ic_placeholder).into(holder.binding.imagePopularItem)

        }catch (e:java.lang.Exception){
            Glide.with(context).load(R.drawable.ic_placeholder).into(holder.binding.imagePopularItem)
        }


    }

    override fun getItemCount(): Int {

        return popularProductResponse.data.size

    }

    public class MYViewHolder(var binding:AllPopularProductsListBinding):RecyclerView.ViewHolder(binding.root)

}