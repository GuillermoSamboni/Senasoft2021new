package com.senasoft2021new.senasoft2021new

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.senasoft2021new.senasoft2021new.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idBtnLoginHuawei.setOnClickListener{
            loginHuwei()
        }

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


}