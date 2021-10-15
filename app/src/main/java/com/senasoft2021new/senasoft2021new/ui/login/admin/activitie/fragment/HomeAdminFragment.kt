package com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.adapters.EventsAdapter
import com.senasoft2021new.senasoft2021new.databinding.FragmentHomeAdminBinding
import com.senasoft2021new.senasoft2021new.models.EventRegister

class HomeAdminFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var _binding:FragmentHomeAdminBinding?=null
    private val binding get() = _binding!!
    private lateinit var eventViewModel: EventViewModel

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
        binding.idBtnHomeAdminCreateEvent.setOnClickListener{findNavController().navigate(R.id.action_homeAdminFragment_to_createEventFragment)}
        eventViewModel=ViewModelProvider(requireActivity()).get(EventViewModel::class.java)
        initReycler()

    }

    /**
     * inicializar el recyclerView
     */
    private fun initReycler() {

        val list= mutableListOf<EventRegister>()
        val adapterEvents=EventsAdapter(list)

        eventViewModel.getEvents(requireContext()).observe(viewLifecycleOwner){

            list.clear()
            list.addAll(it)
            adapterEvents.notifyDataSetChanged()

        }

        binding.idRcyHomeAdminList.apply {
            adapter=adapterEvents
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}
