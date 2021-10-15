package com.senasoft2021new.senasoft2021new.ui.home.activitie

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.ActivityHelpBinding
import com.senasoft2021new.senasoft2021new.huawei.locationKit.LocationService

class HelpActivity : AppCompatActivity() {
    lateinit var binding:ActivityHelpBinding
    var requestPermisoCamera:Int=1
    var requestPermisoStorage:Int=2
    var uriImage: Uri? =null
    lateinit var locationService:LocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()


        locationService=LocationService(this)
        locationService.startrequest()


        binding= ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnCamera.setOnClickListener { permisoCamera() }
        binding.idBtnStorage.setOnClickListener { permisoStorage() }
        binding.idBtmSend.setOnClickListener { sendMessageHelp() }


    }

    /**
     * ENVIAMOS EL MENSAJE DE AYUDA
     */
    private fun sendMessageHelp() {
        if (binding.idTxtMesaageHelp.text!!.isEmpty() && uriImage==null){
            Toast.makeText(this, "Debe llenar los campos para enviar", Toast.LENGTH_SHORT).show()
        }else{
            var intentSend=Intent()
            intentSend.type="images/*"
            intentSend.type="text/plain"
            intentSend.action=Intent.ACTION_VIEW
            val numerodefecto="+57 3163254647"
            val messageSendHelp="https://api.whatsapp.com/send?phone="+ numerodefecto + "&text=${binding.idTxtMesaageHelp.text} \n"+" Ubicacion Persona:${locationService.myLastUbicationLocation}"+ "\n\n"+uriImage

            intentSend.data= Uri.parse(messageSendHelp)
            startActivity(intentSend)
        }
    }

    /**
     * PEDIMOS PERMISO DE ALMACENAMIENTO
     */
    private fun permisoStorage() {
        val permisoStorage=ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val intentStorage=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (permisoStorage==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),requestPermisoStorage)
            }else{
                startActivityForResult(intentStorage, requestPermisoStorage)
            }
        }
    }
    /**
     * PEDIMOS PERMISO DE CAMARA
     */
    private fun permisoCamera() {
        var permisoCamera=ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        var intentCamera=Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (permisoCamera==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),requestPermisoCamera)
            }else{
                startActivityForResult(intentCamera, requestPermisoCamera)
            }
        }
    }
    /**
     * OBTENEMOS EL RESLTADO DE LOS PERMISOS
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==requestPermisoStorage){
            if (data!=null && resultCode==Activity.RESULT_OK){
                uriImage=data.data
                binding.idImageView.setImageURI(uriImage)
            }

        }
    }
}