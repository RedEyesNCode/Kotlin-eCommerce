package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.data.ProductDetailResponseModel
import com.redeyesncode.andromerce.databinding.ProductImageItemBinding

class ProductImageViewPager(var context:Context, var arraylist:ArrayList<ProductDetailResponseModel.Media>, var onImageClickActivity:onImageEvent) :RecyclerView.Adapter<ProductImageViewPager.ImageViewHolder>(){

    lateinit var binding:ProductImageItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        binding = ProductImageItemBinding.inflate(LayoutInflater.from(context),parent,false)



        return ImageViewHolder(binding)


    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context).load(arraylist.get(position).link).placeholder(context.getDrawable(R.drawable.ic_placeholder)).into(binding.ivBanner)

        holder.binding.ivBanner.setOnClickListener {

            onImageClickActivity.onImageClick(position,arraylist.get(position).link.toString())

        }


    }

    override fun getItemCount(): Int {

        return arraylist.size
    }

    interface onImageEvent{
        fun onImageClick(position: Int,link:String)
    }



    class ImageViewHolder(var binding:ProductImageItemBinding):RecyclerView.ViewHolder(binding.root)
}