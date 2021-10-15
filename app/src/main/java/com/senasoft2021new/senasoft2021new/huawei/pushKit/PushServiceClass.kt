package com.senasoft2021new.senasoft2021new.huawei.pushKit

import android.content.ComponentCallbacks
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import java.lang.Exception
import java.lang.reflect.Executable
import kotlin.concurrent.thread

class PushServiceClass : HmsMessageService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.let {
            Log.d("Mensaje recivido", "Mensaje recivido")
        }
    }

    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d("NewToken", "$token")
    }
}
class GetTokenPushService(){
    var handler:Handler= Handler(Looper.getMainLooper())
    fun getToken(context: Context, callback: (String)->Unit){
        thread {
            try {
                var appikey= AGConnectServicesConfig.fromContext(context).getString("client/app_id")
                val token= HmsInstanceId.getInstance(context).getToken(appikey, "HCM")
                handler.post { callback(token) }
            }catch (e:Exception){
                Log.d("ErrorGetToken", e.toString())
            }
        }
    }
}


