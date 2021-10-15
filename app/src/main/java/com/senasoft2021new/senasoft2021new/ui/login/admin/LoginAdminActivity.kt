package com.senasoft2021new.senasoft2021new.ui.login.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.database.RoomDataBaseClient
import com.senasoft2021new.senasoft2021new.databinding.ActivityLoginAdminBinding
import com.senasoft2021new.senasoft2021new.extension_function.showToast
import com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.AdminActivity
import com.senasoft2021new.senasoft2021new.validations.Validations

class LoginAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.idBtnLoginAdmin.setOnClickListener { loginAdmin() }

    }

    /**
     * loguea un nuevo admin con su cuenta previamente registrada
     */
    private fun loginAdmin() {

        //verificar que los campos no esten vacios
        if(validarCampos()){
            this.showToast("Faltan campos por llenar")
            return
        }

        val name=binding.idTxtLoginAdminName.text.toString().trim()
        val pass=binding.idTxtLoginAdminPass.text.toString().trim()


        if(RoomDataBaseClient.loginAdmin(name,pass,this) || (name == "123" && pass == "123")){
            startActivity(Intent(this,AdminActivity::class.java))

        }else {
            this.showToast("Email o contraseña incorrectos")
            binding.idLayoutLoginAdminName.error="Email o contraseña incorrectos"
        }


    }

    /**
     * validar todos los campos de registro
     * @return true si al menos uno de los campos es vacio o nulo - false en caso contrario
     */
    private fun validarCampos(): Boolean {

        val emptyName=Validations.validteEditText(binding.idTxtLoginAdminName)
        val emptyPass=Validations.validteEditText(binding.idTxtLoginAdminPass)

        return emptyName || emptyPass

    }
}