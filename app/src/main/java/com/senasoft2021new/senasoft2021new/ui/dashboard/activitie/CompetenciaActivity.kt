package com.senasoft2021new.senasoft2021new.ui.dashboard.activitie

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.huawei.hms.ads.AdParam
import com.huawei.hms.ads.InterstitialAd
import com.huawei.hms.videoeditor.ui.api.MediaApplication
import com.huawei.hms.videoeditor.ui.api.MediaApplication.START_MODE_IMPORT_FROM_MEDIA
import com.huawei.hms.videoeditor.ui.api.MediaExportCallBack
import com.huawei.hms.videoeditor.ui.api.MediaInfo
import com.huawei.hms.videoeditor.ui.api.VideoEditorLaunchOption
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.ActivityCompetenciaBinding

class CompetenciaActivity : AppCompatActivity() {
    lateinit var binding: ActivityCompetenciaBinding
    var interstitialAd: InterstitialAd? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityCompetenciaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        interstitialAd= InterstitialAd(this)
        interstitialAd!!.adId="testb4znbuh3n2"

        loadAds()


        binding.idBtnEditarVideo.setOnClickListener {
            permiso()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showAds()
    }
    /**
     * verificamos si teinepermiso de almacenamiento
     */
    private fun permiso() {
        var permisoStorage =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permisoStorage == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                101
            )
        } else {
            editarVideo()
        }
    }

    /**
     * usamos kit de editor de video
     */
    private fun editarVideo() {
        MediaApplication.getInstance()
            .setApiKey("CwEAAAAA55+fUPr1sbNHe/3d0EIkw5Insy9GM8iI4R1LuRtb+x1YEegt2z5D1VrFwOjUq00e2rVKtwykFqlpGqjyFbCH1bv9g0o=")
        MediaApplication.getInstance().setLicenseId("104839493")
        MediaApplication.getInstance().setOnMediaExportCallBack(callback)
        var options: VideoEditorLaunchOption =
            VideoEditorLaunchOption.Builder().setStartMode(START_MODE_IMPORT_FROM_MEDIA).build()
        MediaApplication.getInstance().launchEditorActivity(this, options)
    }

    private var callback: MediaExportCallBack = object : MediaExportCallBack {
        override fun onMediaExportSuccess(p0: MediaInfo?) {
            showAds()
        }

        override fun onMediaExportFailed(p0: Int) {
            showAds()
        }
    }


    private fun loadAds() {
        var adsParam= AdParam.Builder().build()
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
}