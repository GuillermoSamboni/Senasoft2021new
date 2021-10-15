package com.senasoft2021new.senasoft2021new.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.senasoft2021new.senasoft2021new.adapters.EventsAdapter
import com.senasoft2021new.senasoft2021new.databinding.FragmentDashboardBinding
import com.senasoft2021new.senasoft2021new.models.EventRegister
import com.senasoft2021new.senasoft2021new.ui.dashboard.activitie.CompetenciaActivity
import com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.fragment.EventViewModel
import com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.fragment.InfoEventFragment

class DashboardFragment : Fragment() {

    private lateinit var eventViewModel: EventViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventViewModel =
            ViewModelProvider(requireActivity()).get(EventViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView()

        binding.idBtnFloatCompetencia.setOnClickListener { startActivity(Intent(requireContext(), CompetenciaActivity::class.java)) }
        return root
    }

    /**
     * cargar todos los eventos en el recyclerView
     */
    private fun initRecyclerView() {


        val list= mutableListOf<EventRegister>()
        val adapterEvent=EventsAdapter(list)

        eventViewModel.getEvents(requireContext()).observe(viewLifecycleOwner){
            list.clear()
            list.addAll(it)

            if(list.isEmpty()){
                binding.idRcyDasBoardList.visibility=View.GONE
                binding.lineardashBoard.visibility=View.VISIBLE
            }


            adapterEvent.notifyDataSetChanged()
        }

        binding.idRcyDasBoardList.apply {
            adapter=adapterEvent
        }


        adapterEvent.setOnClickListener{
            val position=binding.idRcyDasBoardList.getChildAdapterPosition(it)
            val event=list[position]
            eventViewModel.selectEvent(event)
            val infoEvent=InfoEventFragment()
            infoEvent.show(childFragmentManager,"InfoEvent")
        }
        
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}