package com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.FragmentInfoEventBinding
import com.senasoft2021new.senasoft2021new.extension_function.showToast


class InfoEventFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)
    }


    private var _binding:FragmentInfoEventBinding?=null
    private val binding get() = _binding!!
    private lateinit var eventViewModel: EventViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding=FragmentInfoEventBinding.inflate(LayoutInflater.from(context))
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setContentView(binding.root)
        eventViewModel=ViewModelProvider(requireActivity()).get(EventViewModel::class.java)
        binding.idBtnInfoEventSubscrebeME.setOnClickListener { requireContext().showToast("Suscripción exitosa") }
        dialog.window?.setWindowAnimations(R.style.dialog_anim)
        loadInfo()

        return dialog
    }

    private fun loadInfo() {
        eventViewModel.getEvent().observe(this){

            Glide.with(requireContext()).load(it.image).into(binding.idImgInfoEvent)
            binding.idTxtInfoEventTitle.text=it.title
            binding.idTxtInfoEventDescrip.text=it.description
            binding.idTxtInfoEventStartDate.text="Fecha de inicio: ${it.startDate}"
            binding.idTxtInfoEventEndDate.text="Fecha de finalización: ${it.endDate}"

        }
    }


}