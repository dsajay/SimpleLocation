package com.location.mylibrary.location.data_layer

import com.app.demo.location.data_layer.source.GoogleLocationDataSource
import com.location.mylibrary.location.model.LocationDomainModel
import io.reactivex.Flowable

class LocationRepositoryImpl (
    private val googleLocationDataSource: GoogleLocationDataSource
) : LocationRepository {
    override fun getLocation(): Flowable<LocationDomainModel> {
        return googleLocationDataSource.locationObservable.map { it }
    }
}