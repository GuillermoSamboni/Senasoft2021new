package com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.FragmentHomeAdminBinding

class HomeAdminFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var _binding:FragmentHomeAdminBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentHomeAdminBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initReycler()

    }

    /**
     * inicializar el recyclerView
     */
    private fun initReycler() {


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}
