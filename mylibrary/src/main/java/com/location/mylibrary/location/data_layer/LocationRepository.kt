package com.location.mylibrary.location.data_layer

import com.location.mylibrary.location.model.LocationDomainModel
import io.reactivex.Flowable

interface LocationRepository {

    fun getLocation(): Flowable<LocationDomainModel>

}