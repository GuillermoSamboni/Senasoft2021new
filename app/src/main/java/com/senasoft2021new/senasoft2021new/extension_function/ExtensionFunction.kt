package com.senasoft2021new.senasoft2021new.extension_function

import android.content.Context
import android.widget.Toast



fun Context.showToast(message:String){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}