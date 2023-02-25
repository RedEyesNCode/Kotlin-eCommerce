package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redeyesncode.andromerce.data.GetAllProductsResponseModel
import com.redeyesncode.andromerce.databinding.CategoryItemBinding

class SearchAdapter(var context: Context,var searchResults:ArrayList<GetAllProductsResponseModel.Data>, var onEventActivity:SearchAdapter.onEvent):RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var binding: CategoryItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)


        return SearchViewHolder(binding)



    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        binding.rbtnCategory.text = searchResults.get(position).name
        binding.rbtnCategory.setOnClickListener {

            onEventActivity.onSearchClick(position,searchResults.get(position).id.toString())

        }


    }

    override fun getItemCount(): Int {


        return searchResults.size

    }

    interface onEvent{

        fun onSearchClick(position:Int,productId:String)

    }
    class SearchViewHolder(var binding:CategoryItemBinding):RecyclerView.ViewHolder(binding.root)
}