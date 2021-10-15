package com.senasoft2021new.senasoft2021new.ui.notifications

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.huawei.hms.maps.CameraUpdateFactory
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.PointOfInterest
import com.huawei.hms.site.api.SearchResultListener
import com.huawei.hms.site.api.model.DetailSearchResponse
import com.huawei.hms.site.api.model.SearchStatus
import com.huawei.hms.site.api.model.Site
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment(), HuaweiMap.OnPoiClickListener {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null
    lateinit var huaweiMap: HuaweiMap



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.idMapView.onCreate(null)
        binding.idMapView.getMapAsync{map->onMapReady(map)}
        return root
    }

    private fun onMapReady(map: HuaweiMap?) {
        if (map != null) {
            huaweiMap=map
            huaweiMap.apply {
                val animateCamera= CameraUpdateFactory.newLatLngZoom(LatLng(2.433, -76.617), 5f)
                huaweiMap.animateCamera(animateCamera)
                this.isMyLocationEnabled=true
                this.isIndoorEnabled=true
                this.mapType

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPoiClick(poi: PointOfInterest?) {
        var resultClickListenr: SearchResultListener<DetailSearchResponse> = object : SearchResultListener<DetailSearchResponse>{
            override fun onSearchResult(resultSucces: DetailSearchResponse?) {
                var site: Site? =null
                if (resultSucces==null || resultSucces.site.also { site=it }==null){
                    return
                }
                site.let { displayInfoSite(it) }
            }

            private fun displayInfoSite(it: Site?) {
                val alertDialog=AlertDialog.Builder(requireContext())
                alertDialog.setTitle("My Ubicación")
                    .setMessage(it!!.formatAddress)
                    .setCancelable(false)
                    .setPositiveButton("ok"){_,_->
                        alertDialog.setCancelable(true)
                    }.create().show()
            }

            override fun onSearchError(p0: SearchStatus?) {
                Log.d("ErrorPoi", "ErrroPoi")
            }

        }
    }
}