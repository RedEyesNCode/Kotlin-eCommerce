package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.data.AllSubCategoryResponse
import com.redeyesncode.andromerce.databinding.RowTopSellerBinding

class SubCategoryAdapter(var context: Context, var categoryResponseModel: AllSubCategoryResponse,var onEventAct:onEventSubCateogory):
    RecyclerView.Adapter<SubCategoryAdapter.MyViewholder>() {

    lateinit var binding: RowTopSellerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = RowTopSellerBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewholder(binding)
    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {


        holder.binding.tvTopSeller.setText(categoryResponseModel.data.get(position).name)


        Glide.with(context).load(categoryResponseModel.data.get(position).image)
            .placeholder(R.drawable.ic_placeholder).into(holder.binding.imageTopSeller)

        holder.binding.sellerDetailsLayout.setOnClickListener {
            onEventAct.onClickSubCategory(position,categoryResponseModel.data.get(position).subCategoryId!!.toInt(),categoryResponseModel.data.get(position).name)

        }

    }

    override fun getItemCount(): Int {

        return categoryResponseModel.data.size
    }
    interface onEventSubCateogory{
        fun onClickSubCategory(position: Int, subCategoryId: Int, name: String?)

    }


    public class MyViewholder(var binding: RowTopSellerBinding) :
        RecyclerView.ViewHolder(binding.root)
}