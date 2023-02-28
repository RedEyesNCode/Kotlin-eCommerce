package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.data.PopularProductResponse
import com.redeyesncode.andromerce.databinding.AllPopularProductsListBinding

class PopularProductAdapter (var context: Context,var popularProductResponse: PopularProductResponse,var onEventActivity:onEvent):RecyclerView.Adapter<PopularProductAdapter.MYViewHolder>(){

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
//            Glide.with(context).load("https://cdn.zivame.com/media/mimages/home_page_banner_desktop_1400x550_27FEB_Extra150Off_MB_desk.jpg?t=1677496233").placeholder(R.drawable.ic_placeholder).into(holder.binding.imagePopularItem)

        }catch (e:java.lang.Exception){
            Glide.with(context).load(R.drawable.ic_placeholder).into(holder.binding.imagePopularItem)
        }

        holder.binding.popularProductClick.setOnClickListener {
            data.productTable?.id?.let { it1 -> onEventActivity.onClickProduct(position, it1) }
        }


    }

    public interface onEvent{
        fun onClickProduct(position:Int,productId:Int)


    }

    override fun getItemCount(): Int {

        return popularProductResponse.data.size

    }

    public class MYViewHolder(var binding:AllPopularProductsListBinding):RecyclerView.ViewHolder(binding.root)

}