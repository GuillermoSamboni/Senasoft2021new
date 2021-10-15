package com.senasoft2021new.senasoft2021new.huawei.locationKit

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.IntentSender
import android.os.Looper
import android.util.Log
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.common.ResolvableApiException
import com.huawei.hms.location.*
import com.senasoft2021new.senasoft2021new.constantes.Constantes

class LocationService(val context: Context) : LocationCallback() {
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var myLastUbicationLocation: android.location.Location

    fun startrequest() {
        var settingsClient = LocationServices.getSettingsClient(context)
        var myLocationBuilder = LocationSettingsRequest.Builder()
        val myUbi = LocationRequest()
        myUbi.countryCode
        myUbi.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        myUbi.getExtras()

        myLocationBuilder.addLocationRequest(myUbi)

        val locarionRequest = myLocationBuilder.build()
        settingsClient.checkLocationSettings(locarionRequest)
            .addOnSuccessListener(OnSuccessListener { locationSettingsResponse ->
                fusedLocationProviderClient = FusedLocationProviderClient(context)
                fusedLocationProviderClient.requestLocationUpdates(
                    myUbi,
                    this@LocationService,
                    Looper.getMainLooper()
                )
            }).addOnFailureListener { e ->
                when ((e as ResolvableApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        var rae = e
                        rae.startResolutionForResult(context as Activity, 0)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.d("errorLocation", e.toString())
                    }
                }

            }
    }

    override fun onLocationResult(resultLocation: LocationResult?) {
        super.onLocationResult(resultLocation)
        resultLocation?.let {
            myLastUbicationLocation=resultLocation.lastLocation

            Constantes.myUbicationLastTime=myLastUbicationLocation
            var alertDialog=AlertDialog.Builder(context)
            alertDialog.setTitle("My ubicacion")
                .setMessage("${resultLocation.lastLocation.latitude} :: ${resultLocation.lastLocation.longitude}")
                .setCancelable(false)
                .setPositiveButton("Aceptar"){_,_->
                    alertDialog.setCancelable(true)
                }.create().show()
        }
    }
}