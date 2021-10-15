package com.senasoft2021new.senasoft2021new.ui.controller

import android.app.Activity
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.huawei.hms.identity.Address.getAddressClient
import com.huawei.hms.identity.entity.GetUserAddressResult
import com.huawei.hms.identity.entity.UserAddressRequest
import com.huawei.hms.support.api.client.Status
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.ActivityProfileBinding
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBtnFloatIdentity.setOnClickListener {
            identityKit()
        }
    }

    /**
     * Hacemos uso el kit de identity
     */
    private fun identityKit() {
        var task=com.huawei.hms.identity.Address.getAddressClient(this).getUserAddress(UserAddressRequest())

        task.apply {
            addOnCompleteListener {
                try {
                    startActivityForResult(it.result)
                }catch (e:Exception){
                    Log.d("errr identity", e.toString())
                }
            }
        }

    }

    private fun startActivityForResult(result: GetUserAddressResult?) {
        val status: Status= result!!.status
        if (result.returnCode==0 && status.hasResolution()){
            status.startResolutionForResult(this, 0)
        }
    }
}