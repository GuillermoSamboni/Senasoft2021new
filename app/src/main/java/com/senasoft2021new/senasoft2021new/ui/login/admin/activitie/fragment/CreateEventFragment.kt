package com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.huawei.wisesecurity.kfs.validation.constrains.Valid
import com.senasoft2021new.senasoft2021new.database.RoomDataBaseClient
import com.senasoft2021new.senasoft2021new.databinding.FragmentCreateEventBinding
import com.senasoft2021new.senasoft2021new.extension_function.showToast
import com.senasoft2021new.senasoft2021new.models.EventRegister
import com.senasoft2021new.senasoft2021new.validations.Validations
import java.text.SimpleDateFormat
import java.util.*


class CreateEventFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var _binding:FragmentCreateEventBinding?=null
    private val binding get() = _binding!!
    private var startDate:String?=null
    private var endDate:String?=null
    private var uriImage:Uri?=null



    private val permissions=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        onMultiplePermissionsResult(it)
    }
    private val launcherImage=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        onLaunchcerImageResult(it)
    }

    /**
     * hacer algo despues de que se lanze el startActivity para obtener la imagen
     */
    private fun onLaunchcerImageResult(result: ActivityResult) {
        result.data?.data?.let {
            uriImage=it
            Glide.with(requireContext()).load(it).into(binding.idBtnCreateEventPickImage)
        }
    }

    /**
     * recorer los permisos que se piden y realizar una accion de acuerdo a los resultados del permiso
     */
    private fun onMultiplePermissionsResult(results: Map<String, Boolean>) {
        for( (permission, value) in results){
            when(permission){
                Manifest.permission.READ_EXTERNAL_STORAGE-> if(value) intentPickImage() else requireContext().showToast("Debe dar permisos de almacenamiento")

            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCreateEventBinding.inflate(inflater,container,false)

        permissions.launch(arrayOf(Manifest.permission.READ_CALENDAR))


        binding.idBtnCreateEventStartDate.setOnClickListener { pickDate("start")}
        binding.idBtnCreateEventEndDate.setOnClickListener { pickDate("end") }
        binding.idBtnCreateEventPickImage.setOnClickListener { permissions.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)) }

        binding.idBtnCreateEvent.setOnClickListener { createEvent() }

       return binding.root
    }

    /**
     * registrar un nuevo evento a partir de los parametros requeridos
     */
    private fun createEvent() {

        if(validarCampos()){
            requireContext().showToast("Faltan campos por llenar")
            return
        }

        val title=binding.idTxtCreateEventTitle.text.toString().trim()
        val descrip=binding.idTxtCreateEventDescrip.text.toString().trim()

        if(startDate == null){
            requireContext().showToast("Ingrese la fecha de inicio")
            binding.idBtnCreateEventStartDate.playAnimation()
            return
        }
        if(endDate == null){
            requireContext().showToast("Ingrese la fecha de finalización")
            binding.idBtnCreateEventEndDate.playAnimation()
            return
        }

        if(uriImage == null){
            requireContext().showToast("Ingrese una imagen")
            return
        }

        val event=EventRegister(0,title,descrip,uriImage.toString(),startDate!!,endDate!!)

        if(RoomDataBaseClient.registerEvent(event,requireContext())){
            findNavController().popBackStack()
        }else
            requireContext().showToast("Ha ocurrido un error")


    }

    /**
     * validar los campos de registro
     * @return true si al menos uno de los campos es vacio o nulo - false en caso contrario
     */
    private fun validarCampos(): Boolean {

        val emptyTitle=Validations.validteEditText(binding.idTxtCreateEventTitle)
        val emptyDescrip=Validations.validteEditText(binding.idTxtCreateEventDescrip)

        return emptyTitle || emptyDescrip

    }


    /**
     *obtener la fecha y hora
     *@param action si es start se obtendra la fecha de inicio - si es end se obtendra la feecha de finalizacion
     */
    private fun pickDate(action:String){

        val datePicker=MaterialDatePicker.Builder.datePicker().build()
        val timePicker=MaterialTimePicker.Builder().build()
        datePicker.show(childFragmentManager,"")

        datePicker.addOnPositiveButtonClickListener {
            val date=Calendar.getInstance()

            date.time=Date(it)
            timePicker.show(childFragmentManager,"")
            timePicker.addOnPositiveButtonClickListener {
                date.set(Calendar.HOUR, timePicker.hour)
                date.set(Calendar.MINUTE, timePicker.minute)

                val currentDate=SimpleDateFormat("yy-MM-dd:hh-mm").format(date.time).toString()

                when(action){
                    "start"->{
                        startDate=currentDate
                        binding.idTxtCreateEventStartDate.text=currentDate
                    }

                    "end"->{
                        endDate=currentDate
                        binding.idTxtCreateEventEndDate.text=currentDate
                    }
                }


            }

        }




    }

    /**
     * lanzar un intent para obtener una imagen
     */
    private fun intentPickImage(){
        val intent=Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        launcherImage.launch(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}