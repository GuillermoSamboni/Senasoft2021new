package com.senasoft2021new.senasoft2021new.ui.home.activitie.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senasoft2021new.senasoft2021new.database.RoomDataBaseClient
import com.senasoft2021new.senasoft2021new.models.DenunciaRegister

class DenunciaViewModel: ViewModel(){


    private val _denuncias=MutableLiveData<MutableList<DenunciaRegister>>()

    fun getDenuncias(context: Context): MutableLiveData<MutableList<DenunciaRegister>> {
        _denuncias.value = RoomDataBaseClient.lisAllDenuncias(context)

        return _denuncias

    }

}