package com.senasoft2021new.senasoft2021new.ui.controller

import android.app.Activity
import android.location.Address
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.InterstitialAd
import com.huawei.hms.identity.Address.getAddressClient
import com.huawei.hms.identity.entity.GetUserAddressResult
import com.huawei.hms.identity.entity.UserAddressRequest
import com.huawei.hms.support.api.client.Status
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.database.SharedPreferencesClient
import com.senasoft2021new.senasoft2021new.databinding.ActivityProfileBinding
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
     var interstitialAd: InterstitialAd? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        interstitialAd= InterstitialAd(this)
        interstitialAd!!.adId="testb4znbuh3n2"
        getCurrentUser()
        loadAds()


        binding.idBtnFloatIdentity.setOnClickListener {
            identityKit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showAds()
    }
    /**
     * hacemos uso dle kit de ads anuncios
     */
    private fun loadAds() {
        var adsParam=AdParam.Builder().build()
        interstitialAd!!.loadAd(adsParam)
    }

    private fun showAds(){
        if (interstitialAd!=null){
            if (interstitialAd!!.isLoaded){
                interstitialAd!!.show(this)
                finishAfterTransition()
            }
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

    /**
     * obtener la cuenta de usuario y mostrar sus datos
     */
    private fun getCurrentUser(){

        SharedPreferencesClient.getCurrentUser(this)?.let {

            Glide.with(this).load(it.qrCode).into(binding.idImgProfileQrCode)
            binding.idTxtProfileName.text=it.name
            binding.idTxtProfilePhone.text=it.phone
            binding.idTxtProfileEmail.text=it.email

        }

    }

}