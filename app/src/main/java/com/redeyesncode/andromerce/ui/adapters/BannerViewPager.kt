package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.data.BannersResponseModel
import com.redeyesncode.andromerce.databinding.BannerItemBinding

class BannerViewPager(var context:Context,var banners:BannersResponseModel):RecyclerView.Adapter<BannerViewPager.MyViewholder>() {

    lateinit var binding: BannerItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {
        binding = BannerItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return MyViewholder(binding)


    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        Glide.with(context).load(banners.data.get(position).bannerLink).placeholder(R.drawable.ic_placeholder).into(holder.binding.ivBanner)


    }

    override fun getItemCount(): Int {


        return banners.data.size
    }

    public class MyViewholder(var binding:BannerItemBinding):RecyclerView.ViewHolder(binding.root)
}