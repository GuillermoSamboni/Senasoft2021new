package com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.FragmentCreateEventBinding
import com.senasoft2021new.senasoft2021new.databinding.FragmentHomeAdminBinding


class CreateEventFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var _binding:FragmentCreateEventBinding?=null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCreateEventBinding.inflate(inflater,container,false)
       return binding.root
    }


}