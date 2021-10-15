package com.senasoft2021new.senasoft2021new.ui.login.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.senasoft2021new.senasoft2021new.MainActivity
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.database.RoomDataBaseClient
import com.senasoft2021new.senasoft2021new.databinding.ActivityRegisterBinding
import com.senasoft2021new.senasoft2021new.extension_function.showToast
import com.senasoft2021new.senasoft2021new.models.UserRegister
import com.senasoft2021new.senasoft2021new.validations.Validations
import javax.xml.validation.Validator

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnRegisterMe.setOnClickListener { registerUser() }

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
        if (emailReal(email)){
            this.showToast("Ingrese un email valido")
            binding.idTxtLaoutRegisterName.error="Ingrese un email valido"
            return
        }else
            binding.idTxtLaoutRegisterName.error=""

        //verificar si el nombre ya esta registrado
        if(RoomDataBaseClient.nameUserExits(name,this)){
            this.showToast("Este nombre ya está registrado")
            binding.idTxtLaoutRegisterName.error="El nombre ya está registrado"
            return
        }else
            binding.idTxtLaoutRegisterName.error=""

        //verificar si el email ya esta registrado
        if(RoomDataBaseClient.emailUserExits(email,this)){
            this.showToast("Este email ya está registrado")
            binding.idTxtLaoutRegisterEmail.error="El nombre ya está registrado"
            return
        }
        else
            binding.idTxtLaoutRegisterEmail.error=""


        //verificar que las contraseñas coincidan

        if(pass.lowercase() != confirmPass.lowercase()){
            binding.idTxtLaoutRegisterPass.error="Las contraseñas no coinciden"
            binding.idLayoutRegisterConfirmPass.error="Las contraseñas no coinciden"
            return
        }else
            binding.idTxtLaoutRegisterPass.error=""
        binding.idLayoutRegisterConfirmPass.error=""


        //registrar el usurio
        val user=UserRegister(0,name, phone, email,pass)

        if(RoomDataBaseClient.registerUser(user,this)){
            startActivity(Intent(this,MainActivity::class.java))
        }else
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
    private fun emailReal(email: String) =Patterns.EMAIL_ADDRESS.matcher(email).matches()


}