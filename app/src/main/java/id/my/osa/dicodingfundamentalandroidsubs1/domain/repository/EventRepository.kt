package id.my.osa.dicodingfundamentalandroidsubs1.domain.repository

import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event

interface EventRepository {
    suspend fun getUpcomingEvents(query: String? = null): List<Event>
    suspend fun getFinishedEvents(query: String? = null): List<Event>
    suspend fun getBannerEvents(): List<Event>
    suspend fun getEventDetail(id: Int): Event
}
