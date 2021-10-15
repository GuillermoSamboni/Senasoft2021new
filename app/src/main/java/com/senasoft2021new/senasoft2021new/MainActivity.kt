package com.senasoft2021new.senasoft2021new

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.senasoft2021new.senasoft2021new.database.RoomDataBaseClient
import com.senasoft2021new.senasoft2021new.databinding.ActivityMainBinding
import com.senasoft2021new.senasoft2021new.extension_function.showToast
import com.senasoft2021new.senasoft2021new.ui.login.admin.LoginAdminActivity
import com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.AdminActivity
import com.senasoft2021new.senasoft2021new.ui.login.user.RegisterActivity
import com.senasoft2021new.senasoft2021new.validations.Validations

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)
        binding.idBtnLoginHuawei.setOnClickListener{
            loginHuwei()
        }

        binding.idBtnLoginRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        binding.idBtnLoginGoToAdmin.setOnClickListener {
            startActivity(Intent(this,AdminActivity::class.java))
        }

        binding.idBtnLogin.setOnClickListener { loginUser() }

    }
    //USAMOS KIT DE CUENTA
    private fun loginHuwei() {
        var myAuthParam= HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setId()
            .setIdToken()
            .setAccessToken()
            .setUid()
            .setProfile()
            .setEmail()
            .createParams()
        val myAuthManager= HuaweiIdAuthManager.getService(this, myAuthParam)
        startActivityForResult(myAuthManager.signInIntent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100){
            if (resultCode== RESULT_CANCELED){
                Toast.makeText(this, "Login Cancelado", Toast.LENGTH_SHORT).show()
            }else if (resultCode==Activity.RESULT_OK){
                var taskLogin=HuaweiIdAuthManager.parseAuthResultFromIntent(data)
                if (taskLogin.isSuccessful){
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, InicioActivity::class.java))
                }else{
                    Toast.makeText(this, "Revisa tu conexion a intenet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    /**
     * loguear un nuevo usuario en la base de datos
     */
    private fun loginUser(){

        if(validarCampos()){
            this.showToast("Faltan campos por completar")
            return
        }

        val name=binding.idTxtLoginName.text.toString().trim()
        val pass=binding.idTxtLoginPass.text.toString().trim()

        if(RoomDataBaseClient.loginUser(name,pass,this)){
            startActivity(Intent(this,InicioActivity::class.java))
        }
        else{
            binding.idLayoutLoginName.error="Nombre o contraseña incorrectas"
            this.showToast("Nombre o contraseña incorrectas")
        }

    }

    /**
     * validar los campos de registro
     * @return true si al menos uno de los campos esta vacio o es nulo
     *  - false en caso contrario
     */
    private fun validarCampos(): Boolean {

        val emptyName=Validations.validteEditText(binding.idTxtLoginName)
        val emptyPass=Validations.validteEditText(binding.idTxtLoginPass)

        return emptyName || emptyPass

    }


}