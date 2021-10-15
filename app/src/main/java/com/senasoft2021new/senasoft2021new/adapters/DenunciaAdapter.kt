package com.senasoft2021new.senasoft2021new.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.senasoft2021new.senasoft2021new.databinding.ItemDenunciaBinding
import com.senasoft2021new.senasoft2021new.models.DenunciaRegister

class DenunciaAdapter(private val list:MutableList<DenunciaRegister>): RecyclerView.Adapter<DenunciaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemDenunciaBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

    class ViewHolder(private val binding:ItemDenunciaBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(denuncia:DenunciaRegister){
            binding.idTxtItemDeunciaTitle.text=denuncia.title
            binding.idTxtItemDeunciaMessage.text=denuncia.description
            Glide.with(binding.idImgItemDenuncia).load(denuncia.image).into(binding.idImgItemDenuncia)
        }

    }

}