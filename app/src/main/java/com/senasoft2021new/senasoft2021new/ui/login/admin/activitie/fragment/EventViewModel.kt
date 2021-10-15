package com.senasoft2021new.senasoft2021new.ui.login.admin.activitie.fragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senasoft2021new.senasoft2021new.database.RoomDataBaseClient
import com.senasoft2021new.senasoft2021new.models.EventRegister

class EventViewModel:ViewModel() {

    private val _event=MutableLiveData<EventRegister>()
    private val _events=MutableLiveData<MutableList<EventRegister>>()


    /**
     * obtener el evento seleccionado
     */
    fun getEvent()=_event

    /**
     * selecionar un evento
     */
    fun selectEvent(eventRegister: EventRegister){
        _event.value=eventRegister
    }

    /**
     * obtener todos los eventos registrados en la bd
     */
    fun getEvents(context: Context): MutableLiveData<MutableList<EventRegister>> {
        _events.value=RoomDataBaseClient.listAllEvents(context)
        return _events
    }


}