package com.location.mylibrary.location.domain_layer

import com.location.mylibrary.location.data_layer.LocationRepository
import com.location.mylibrary.location.model.LocationDomainModel
import io.reactivex.Flowable

class GetLocationUseCase( private val locationRepository: LocationRepository) {
    fun build() : Flowable<LocationDomainModel> {
        return locationRepository.getLocation()
    }
}