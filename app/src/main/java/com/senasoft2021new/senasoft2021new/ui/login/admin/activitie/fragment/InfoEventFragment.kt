package com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.FragmentInfoEventBinding


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
        eventViewModel=ViewModelProvider(requireActivity()).get(EventViewModel::class.java)

        loadInfo()

        return dialog
    }

    private fun loadInfo() {
        eventViewModel.getEvent().observe(this){

        }
    }


}