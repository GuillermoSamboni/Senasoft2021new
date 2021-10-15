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
import android.renderscript.ScriptGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.ActivityDenunciaBinding

class DenunciaActivity : AppCompatActivity() {
    lateinit var binding:ActivityDenunciaBinding
    var uriImgage:Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDenunciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idImageDenuncia.setOnClickListener { permisoStorage()}
    }

    /**
     * PEDIMOS PERMISO DE ALMACENAMIENTO
     */
    private fun permisoStorage() {
        val permisoStorage= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        val intentStorage= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if (permisoStorage== PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),4)
            }else{
                startActivityForResult(intentStorage, 4)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==4){
            if (resultCode==Activity.RESULT_OK && data!=null){
                uriImgage=data.data
                binding.idImageDenuncia.setImageURI(uriImgage)

                sendMesageDenuncia()
            }else{
                Toast.makeText(this, "No selwciono nifuna imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendMesageDenuncia() {
        if (binding.idTxtTitleDenunia.text.isEmpty() && binding.idTxtMessageDenuncia.text.isEmpty()){
            Toast.makeText(this, "Debbe llenar los cmapos anteriores", Toast.LENGTH_SHORT).show()
        }else{
            var intentSend=Intent()
            intentSend.action=Intent.ACTION_VIEW
            intentSend.type="images/*"
            intentSend.type="text/plain"

            var numeroDefecto="+57 3163254647"
            var mesageDenuncia="https://api.whatsapp.com/send?phone="+ numeroDefecto +
                    "&text="+binding.idTxtTitleDenunia.text.toString() +"\n" +
                     binding.idTxtMessageDenuncia.text.toString() + "\n" + uriImgage

            intentSend.data= Uri.parse(mesageDenuncia)
            startActivity(intentSend)
        }
    }


}