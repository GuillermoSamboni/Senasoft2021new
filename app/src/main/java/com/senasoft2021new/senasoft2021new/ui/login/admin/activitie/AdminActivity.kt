package com.senasoft2021new.senasoft2021new.ui.login.admin.activitie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {

    private  lateinit var binding:ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.idBtnScaneo.setOnClickListener { startActivity(Intent(this,ScaneoActivity::class.java)) }


    }
}