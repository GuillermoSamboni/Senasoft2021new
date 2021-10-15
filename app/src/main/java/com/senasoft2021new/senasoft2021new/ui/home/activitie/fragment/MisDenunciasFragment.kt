package com.senasoft2021new.senasoft2021new.ui.home.activitie.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.adapters.DenunciaAdapter
import com.senasoft2021new.senasoft2021new.database.RoomDataBaseClient
import com.senasoft2021new.senasoft2021new.databinding.FragmentMisDenunciasBinding
import com.senasoft2021new.senasoft2021new.extension_function.showToast
import com.senasoft2021new.senasoft2021new.models.DenunciaRegister


class MisDenunciasFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.full_screen_dialog)
    }

    private var _binding:FragmentMisDenunciasBinding?=null
    private val binding get() = _binding!!
    private lateinit var denuncia:DenunciaViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentMisDenunciasBinding.inflate(LayoutInflater.from(context))
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setContentView(binding.root)
        denuncia=ViewModelProvider(requireActivity()).get(DenunciaViewModel::class.java)
        dialog.window?.setWindowAnimations(R.style.dialog_anim)


        initRecycler()

        return dialog
    }

    /**
     * cargar todas las denuncias y mostrarlas en le recyclerView
     */
    private fun initRecycler() {

        val list= mutableListOf<DenunciaRegister>()
        val adapterDenuncias=DenunciaAdapter(list)

        denuncia.getDenuncias(requireContext()).observe(this){

            list.clear()
            list.addAll(it)
            if(list.isEmpty()){
                binding.idRcyMisDenuncias.visibility= View.GONE
                binding.linearMisDenuncias.visibility=View.VISIBLE
            }
            adapterDenuncias.notifyDataSetChanged()
        }



        binding.idRcyMisDenuncias.apply {
            adapter = adapterDenuncias

        }

    }


}