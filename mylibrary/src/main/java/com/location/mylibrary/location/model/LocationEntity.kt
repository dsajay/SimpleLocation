package com.location.mylibrary.location.model

data class LocationEntity(
    var latitude: Double=0.0,
    var longitude: Double=0.0
)
fun LocationDomainModel.mapToPresentation(): LocationEntity {
    return LocationEntity(latitude, longitude)
}