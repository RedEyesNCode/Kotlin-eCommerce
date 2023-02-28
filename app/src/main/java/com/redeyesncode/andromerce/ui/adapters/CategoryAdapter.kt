package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.data.CategoryResponseModel
import com.redeyesncode.andromerce.databinding.RowTopSellerBinding

class CategoryAdapter(var context: Context,var categoryResponseModel: CategoryResponseModel, var onEventCategory:onEventCategoryClick):RecyclerView.Adapter<CategoryAdapter.MyViewholder>() {

    lateinit var binding: RowTopSellerBinding

    interface onEventCategoryClick{

        fun onCategoryClick(position: Int,categoryId:Int,name:String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = RowTopSellerBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {


        holder.binding.tvTopSeller.setText(categoryResponseModel.data.get(position).name)
        holder.binding.sellerDetailsLayout.setOnClickListener {
            onEventCategory.onCategoryClick(position,categoryResponseModel.data.get(position).id!!.toInt(),categoryResponseModel.data.get(position).name.toString())

        }

        Glide.with(context).load(categoryResponseModel.data.get(position).image).placeholder(R.drawable.ic_placeholder).into(holder.binding.imageTopSeller)

    }

    override fun getItemCount(): Int {

        return categoryResponseModel.data.size
    }

    public class MyViewholder(var binding:RowTopSellerBinding):RecyclerView.ViewHolder(binding.root)
}