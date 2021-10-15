package com.senasoft2021new.senasoft2021new.huawei.pushKit

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.huawei.hms.push.HmsMessaging
import java.lang.Exception

class Subscribe {
    fun subscribeEvent(context: Context, topic:String){
        try {
            HmsMessaging.getInstance(context).subscribe(topic)
                .addOnCompleteListener {task->
                    if (task.isSuccessful){
                        val alertDialog=AlertDialog.Builder(context)
                        alertDialog.setTitle("SUBSCRIPCION EXITOSA")
                            .setMessage("te has subscrito a : $topic")
                            .setCancelable(false)
                            .setPositiveButton("aceptar"){_,_->
                                alertDialog.setCancelable(true)
                            }.create().show()
                    }else{
                        val alertDialog=AlertDialog.Builder(context)
                        alertDialog.setTitle("SUBSCRIPCION FALLIDA")
                            .setMessage("No ha sido posible subscrubirse a : $topic")
                            .setCancelable(false)
                            .setPositiveButton("aceptar"){_,_->
                                alertDialog.setCancelable(true)
                            }.create().show()
                    }
                }
        }catch (e:Exception){
            Log.d("ErrorSubscripcion", e.toString())
        }
    }
}