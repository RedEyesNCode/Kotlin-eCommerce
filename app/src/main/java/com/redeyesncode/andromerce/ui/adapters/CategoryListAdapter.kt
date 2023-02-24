package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redeyesncode.andromerce.R
import com.redeyesncode.andromerce.data.CategoryResponseModel
import com.redeyesncode.andromerce.databinding.CategoryItemBinding

class CategoryListAdapter(var context: Context,var data:ArrayList<CategoryResponseModel.Data>,var onEventActivity:onEvent):RecyclerView.Adapter<CategoryListAdapter.CategoryListViewHolder>() {

    lateinit var binding: CategoryItemBinding
    var selected_position = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListViewHolder {
        binding = CategoryItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryListViewHolder, position: Int) {
        holder.binding.rbtnCategory.text = data.get(position).name

        if (position == selected_position) {
            holder.binding.rbtnCategory.setTextColor(context.getColor(R.color.pink))
        } else {
            holder.binding.rbtnCategory.setTextColor(context.getColor(R.color.black))
        }
        holder.binding.rbtnCategory.setOnClickListener {
            onEventActivity.onCategoryClick(position,data.get(position).id.toString())

            selected_position = position
            notifyDataSetChanged()


        }

    }

    public interface onEvent{
        fun onCategoryClick(position: Int, categoryId: String)


    }

    override fun getItemCount(): Int {
        return data.size
    }

    class CategoryListViewHolder(var binding:CategoryItemBinding) :RecyclerView.ViewHolder(binding.root)
}