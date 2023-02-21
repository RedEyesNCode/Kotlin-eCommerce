package com.redeyesncode.andromerce.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.redeyesncode.andromerce.data.UserAddressResponseModel
import com.redeyesncode.andromerce.databinding.AddressItemBinding

class AddressAdapter(var context: Context,var onEventAct:onEventAddress,var data:ArrayList<UserAddressResponseModel.Data>):RecyclerView.Adapter<AddressAdapter.MyViewholder>() {

    lateinit var binding: AddressItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewholder {

        binding = AddressItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return MyViewholder(binding)

    }

    override fun onBindViewHolder(holder: MyViewholder, position: Int) {
        holder.binding.tvFullAddress.text = "Address : "+data.get(position).addressLine1+" \n "+data.get(position).addressLine2
        holder.binding.tvCountry.text = "Country : "+data.get(position).country
        holder.binding.tvCity.text = "City : "+data.get(position).city
        holder.binding.tvPostalCode.text = "Postal Code : "+data.get(position).postalCode

        holder.binding.tvMobileNumber.text = "Mobile Number : "+data.get(position).telephone


        holder.binding.ivDelete.setOnClickListener {
            onEventAct.onDeleteClick(position, data.get(position).id!!)
        }

        holder.binding.ivEdit.setOnClickListener {

            onEventAct.onEditClick(position,data.get(position))


        }





    }

    public interface onEventAddress{

        fun onDeleteClick(position: Int,addressId:Int)
        fun onEditClick(position: Int,data :UserAddressResponseModel.Data)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    public class MyViewholder(var binding:AddressItemBinding) :RecyclerView.ViewHolder(binding.root)

}