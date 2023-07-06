package com.app.demo.location.data_layer.source

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.location.mylibrary.location.model.LocationDomainModel
import com.google.android.gms.location.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

private const val LOCATION_REQUEST_INTERVAL = 10000L
private const val LOCATION_REQUEST_FASTEST_INTERVAL = 5000L

class GoogleLocationDataSource constructor(context: Context) {

    private val locationSubject = PublishSubject.create<LocationDomainModel>()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest: LocationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,LOCATION_REQUEST_FASTEST_INTERVAL).build()

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.forEach(::setLocation)
        }
    }

    val locationObservable: Flowable<LocationDomainModel> = locationSubject.toFlowable(
        BackpressureStrategy.MISSING)
        .doOnSubscribe { startLocationUpdates() }
        .doOnCancel { stopLocationUpdates() }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.lastLocation.addOnSuccessListener(::setLocation)
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun setLocation(location: Location) {
        locationSubject.onNext(
            LocationDomainModel(
                location.latitude,
                location.longitude
            )
        )
    }
}