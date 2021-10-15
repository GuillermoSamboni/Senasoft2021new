package com.senasoft2021new.senasoft2021new.ui.login.admin.activitie

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.maps.internal.HmsUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.senasoft2021new.senasoft2021new.databinding.ActivityScaneoBinding

class ScaneoActivity : AppCompatActivity() {
    lateinit var binding:ActivityScaneoBinding

    private var DEFAULT_VIEW=0x22
    private var REQUES_CODE_SCAN=0x01

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityScaneoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idBtnScaneoCodigoQr.setOnClickListener { scanearCodigo() }

    }

    private fun scanearCodigo() {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE), DEFAULT_VIEW)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val options=HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create()
        if (requestCode==REQUES_CODE_SCAN && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED && grantResults.size<2 ){
            return
        }
        if (requestCode==DEFAULT_VIEW){
            ScanUtil.startScan(this, REQUES_CODE_SCAN, options)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==Activity.RESULT_OK &&  data==null){
            Toast.makeText(this, "No eligio ningun cosdigo para scanear", Toast.LENGTH_SHORT).show()
            return
        }
        if (requestCode== REQUES_CODE_SCAN){
            var obj=data?.getParcelableExtra(ScanUtil.RESULT) as HmsScan?
            obj?.showResult
            val alerDialog= AlertDialog.Builder(this)
            alerDialog.setCancelable(false)
                .setTitle("RESULTADO DEL SCANEO")
                .setMessage(" ${obj?.showResult}")
                .setPositiveButton("Siguiente"){_,_->
                    alerDialog.setCancelable(false)
                }
            alerDialog.create().show()
            
        }
    }

}