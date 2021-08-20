package com.example.yourmeduser.ui.map

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.yourmeduser.R
import com.example.yourmeduser.data.Medicine
import com.example.yourmeduser.data.QueryResults
import com.example.yourmeduser.data.Service

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    lateinit var mGoogleMap: GoogleMap
    lateinit var passedData: QueryResults

    private val callback = OnMapReadyCallback { googleMap ->
        mGoogleMap = googleMap
        val nairobi = LatLng(-1.2921, 36.8219)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(nairobi))
        googleMap.setMaxZoomPreference(15.0f)
        googleMap.setMinZoomPreference(10.0f)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        passedData = requireArguments().getParcelable("results")!!

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    private fun showServices() {
        val services = passedData.services

        val marker = ArrayList<Marker>()
        for(service in services){
            val location = LatLng(service.provider!!.coordinate!!.latitude, service.provider!!.coordinate!!.longitude)
            marker.add(mGoogleMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(service.serviceName+"\n"+ service.provider!!.name)
                //.icon()

            ))


        }

        mGoogleMap.setOnInfoWindowClickListener {
            val  latLng = it.position
            var service: Service? = null

            for(aService in services){
                if (aService.provider!!.coordinate?.latitude == latLng.latitude && aService.provider!!.coordinate?.longitude == latLng.longitude)
                    service = aService
            }
                if (service != null) {
                  //  placeOrder(shop)
                }


            }
        }


    private fun showMedicines() {
        val medicines = passedData.medicines

        val marker = ArrayList<Marker>()
        for(medicine in medicines){
            val location = LatLng(medicine.provider!!.coordinate!!.latitude, medicine.provider!!.coordinate!!.longitude)
            marker.add(mGoogleMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(medicine.genericName+"\n"+ medicine.provider!!.name)
                //.icon()
            ))


        }

        mGoogleMap.setOnInfoWindowClickListener {
            val  latLng = it.position
            var medicine: Medicine? = null

            for(aMedicine in medicines){
                if (aMedicine.provider!!.coordinate?.latitude == latLng.latitude && aMedicine.provider!!.coordinate?.longitude == latLng.longitude)
                    medicine = aMedicine
            }
            if (medicine != null) {
                //  placeOrder(shop)
            }


        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        if(!passedData.medicines.isEmpty()){
            showMedicines()
        }else if(!passedData.services.isEmpty()){
            showServices()
        }

    }
}