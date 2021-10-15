package com.senasoft2021new.senasoft2021new.ui.login.user

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.maps.internal.HmsUtil
import com.huawei.hms.ml.scan.HmsBuildBitmapOption
import com.huawei.hms.ml.scan.HmsScan
import com.senasoft2021new.senasoft2021new.MainActivity
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.database.RoomDataBaseClient
import com.senasoft2021new.senasoft2021new.databinding.ActivityRegisterBinding
import com.senasoft2021new.senasoft2021new.extension_function.showToast
import com.senasoft2021new.senasoft2021new.models.UserRegister
import com.senasoft2021new.senasoft2021new.validations.Validations
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.OutputStream
import java.lang.Exception
import javax.xml.validation.Validator

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    lateinit var codeBar: Bitmap

    companion object {
        const val SAVE_CODE = 2
        const val BITMAP = 0x22
        lateinit var CODE_BAR: Bitmap
        const val WIDTH = 150
        const val HEIGHT = 150
        const val REQUEST_CPDE_SCAN = 0x23

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.idBtnRegisterMe.setOnClickListener { registerUser() }
        binding.idBtnRegisterGenerateQr.setOnClickListener { saveCodeQr() }

    }

    /**
     * Usamos kit de scan para generar el codigo qr
     */
    private fun saveCodeQr() {
        val name = binding.idTxtRegisterName.text
        val email = binding.idTxtRegisterEmail.text
        val phone = binding.idTxtRegisterPhone.text

        if (name!!.isEmpty() && email!!.isEmpty() && phone!!.isEmpty()) {
            Toast.makeText(this, "Debe llenar los datos del registro", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                SAVE_CODE
            )
        }
    }

    /**
     * guardamos en la galeria el codigo
     */
    fun savecODEqRToGallery(context: Context, bitmap: Bitmap, albumName: String) {
        val file = "${System.currentTimeMillis()}/.jpg"
        val write: (OutputStream) -> Boolean = {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.ImageColumns.MIME_TYPE, "images/*")
                put(MediaStore.Images.ImageColumns.DISPLAY_NAME, file)
                put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            }
            context.contentResolver.let {
                it.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let { uri ->
                    it.openOutputStream(uri).let { (write) }
                }
            }
        } else {
            var imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .toString() + File.pathSeparator + albumName
            var fileNmae = File(imageDir)
            if (fileNmae.exists()) {
                fileNmae.mkdir()
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            return
        }
        if (requestCode == BITMAP) {
            val inetntPick =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            inetntPick.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "imagess/*")
        }
        if (requestCode == SAVE_CODE) {
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                val datosCode =
                    binding.idTxtRegisterName.text.toString() + "\n" + binding.idTxtRegisterPhone.text.toString() + "\n" + binding.idTxtRegisterEmail.text.toString()
                val typeCoe = HmsScan.QRCODE_SCAN_TYPE
                val options = HmsBuildBitmapOption.Creator().setBitmapBackgroundColor(Color.WHITE)
                    .setBitmapColor(Color.BLACK).setBitmapMargin(3).create()
                try {
                    CODE_BAR = ScanUtil.buildBitmap(datosCode, typeCoe, WIDTH, HEIGHT, options)
                    codeBar = CODE_BAR
                    savecODEqRToGallery(this, codeBar, "CodesNew")
                    Toast.makeText(this, "Codigo creado", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.d("ErrorCodigo", e.toString())
                }
            }
        }
    }

    /**
     * registrar un nuevo usuario en la base de datos
     */
    private fun registerUser() {

        if (validarCampos()) {
            this.showToast("Faltan campos por completar")
            return
        }

        val name = binding.idTxtRegisterName.text.toString().trim()
        val phone = binding.idTxtRegisterPhone.text.toString().trim()
        val email = binding.idTxtRegisterEmail.text.toString().trim()
        val pass = binding.idTxtRegisterPass.text.toString().trim()
        val confirmPass = binding.idTxtRegisterConfirmPass.text.toString().trim()

        //verificar si el email es real
        if (!emailReal(email)) {
            this.showToast("Ingrese un email valido")
            binding.idTxtLaoutRegisterEmail.error = "Ingrese un email valido"
            return
        } else
            binding.idTxtLaoutRegisterEmail.error = ""

        //verificar si el nombre ya esta registrado
        if (RoomDataBaseClient.nameUserExits(name, this)) {
            this.showToast("Este nombre ya está registrado")
            binding.idTxtLaoutRegisterName.error = "El nombre ya está registrado"
            return
        } else
            binding.idTxtLaoutRegisterName.error = ""

        //verificar si el email ya esta registrado
        if (RoomDataBaseClient.emailUserExits(email, this)) {
            this.showToast("Este email ya está registrado")
            binding.idTxtLaoutRegisterEmail.error = "El nombre ya está registrado"
            return
        } else
            binding.idTxtLaoutRegisterEmail.error = ""


        //verificar que las contraseñas coincidan

        if (pass.lowercase() != confirmPass.lowercase()) {
            binding.idTxtLaoutRegisterPass.error = "Las contraseñas no coinciden"
            binding.idLayoutRegisterConfirmPass.error = "Las contraseñas no coinciden"
            return
        } else
            binding.idTxtLaoutRegisterPass.error = ""
        binding.idLayoutRegisterConfirmPass.error = ""


        //registrar el usurio
        var ouPutStream = ByteArrayOutputStream()
        codeBar.compress(Bitmap.CompressFormat.PNG, 100, ouPutStream)

        val user = UserRegister(0, name, phone, email, pass, ouPutStream.toByteArray())

        if (RoomDataBaseClient.registerUser(user, this)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else
            this.showToast("Ha ocurrido un error")
    }

    /**
     * validar todos los campos de registro
     * @return true si al menos uno de los campos está vacio
     *  - false en caso contrario
     */
    private fun validarCampos(): Boolean {

        val emtyName = Validations.validteEditText(binding.idTxtRegisterName)
        val emtyPhone = Validations.validteEditText(binding.idTxtRegisterPhone)
        val emtyEmail = Validations.validteEditText(binding.idTxtRegisterEmail)
        val emtyPass = Validations.validteEditText(binding.idTxtRegisterPass)
        val emtyConfrimPass = Validations.validteEditText(binding.idTxtRegisterConfirmPass)

        return emtyName || emtyPhone || emtyEmail || emtyPass || emtyConfrimPass

    }


    /**
     * verificar si el email ingresado es real
     * @return true si el email es real - false en caso contrario
     */
    private fun emailReal(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()


}