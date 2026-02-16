package id.my.osa.dicodingfundamentalandroidsubs1.data.mapper

import id.my.osa.dicodingfundamentalandroidsubs1.data.local.entity.FavoriteEventEntity
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event

fun Event.toEntity(): FavoriteEventEntity = FavoriteEventEntity(
    id = id,
    name = name,
    summary = summary,
    description = description,
    imageLogo = imageLogo,
    mediaCover = mediaCover,
    link = link,
    ownerName = ownerName,
    cityName = cityName,
    quota = quota,
    registrants = registrants,
    beginTime = beginTime,
    endTime = endTime,
    category = category
)

fun FavoriteEventEntity.toDomain(): Event = Event(
    id = id,
    name = name,
    summary = summary,
    description = description,
    imageLogo = imageLogo,
    mediaCover = mediaCover,
    link = link,
    ownerName = ownerName,
    cityName = cityName,
    quota = quota,
    registrants = registrants,
    beginTime = beginTime,
    endTime = endTime,
    category = category
)
