package com.location.mylibrary.location.app_layer


import android.util.Log
import androidx.lifecycle.ViewModel
import com.location.mylibrary.location.domain_layer.GetLocationUseCase
import com.location.mylibrary.location.model.LocationEntity
import com.location.mylibrary.location.model.mapToPresentation
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class TrackViewModel  constructor(private val getLocationUseCase: GetLocationUseCase) : ViewModel() {
    private val _locationModel = MutableStateFlow(LocationEntity(0.0, 0.0))
    val locationModel = _locationModel.asStateFlow()
    fun onLocationPermissionGranted() {

        val observeOn = Schedulers.trampoline()
        getLocationUseCase.build().subscribeOn(observeOn).run {
            this.subscribe {
                Log.e("TrackViewModel", "onLocationPermissionGranted: $it ")
                _locationModel.value=it.mapToPresentation()
            }
        }
    }

}