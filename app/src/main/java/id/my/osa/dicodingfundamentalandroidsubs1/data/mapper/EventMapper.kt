package id.my.osa.dicodingfundamentalandroidsubs1.data.mapper

import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.DetailEventResponse
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.EventResponse
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event

fun EventResponse.ListEventsItem.toDomain(): Event = Event(
    id = id ?: 0,
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

fun DetailEventResponse.Event.toDomain(): Event = Event(
    id = id ?: 0,
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
