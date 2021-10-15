package com.senasoft2021new.senasoft2021new.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.senasoft2021new.senasoft2021new.databinding.ItemEventBinding
import com.senasoft2021new.senasoft2021new.models.EventRegister


class EventsAdapter(private val list: MutableList<EventRegister>): RecyclerView.Adapter<EventsAdapter.ViewHolder>(), View.OnClickListener {

    private var clickListener:View.OnClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val binding=ItemEventBinding.inflate(inflater,parent,false)
        binding.root.setOnClickListener(this)
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount()=list.size

    fun setOnClickListener(listener: View.OnClickListener){
        clickListener=listener
    }

    override fun onClick(v: View?) {
        clickListener?.onClick(v)
    }


    class ViewHolder(private val binding:ItemEventBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(eventRegister: EventRegister) {
            Glide.with(binding.idImgItemEvent).load(eventRegister.image).into(binding.idImgItemEvent)
            binding.idTxtItemEventTitle.text=eventRegister.title
            binding.idTxtItemEventDescrip.text=eventRegister.description
        }

    }

}