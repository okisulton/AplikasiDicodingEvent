package id.my.osa.dicodingfundamentalandroidsubs1.data.repository

import id.my.osa.dicodingfundamentalandroidsubs1.data.mapper.toDomain
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiService
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.repository.EventRepository

class EventRepositoryImpl(
    private val apiService: ApiService
) : EventRepository {

    override suspend fun getUpcomingEvents(query: String?): List<Event> {
        val response = apiService.getEvents(active = 1, query = query)
        return response.listEvents.map { it.toDomain() }
    }

    override suspend fun getFinishedEvents(query: String?): List<Event> {
        val response = apiService.getEvents(active = 0, query = query)
        return response.listEvents.map { it.toDomain() }
    }

    override suspend fun getBannerEvents(): List<Event> {
        val response = apiService.getEvents(limit = 10)
        return response.listEvents.take(5).map { it.toDomain() }
    }

    override suspend fun getEventDetail(id: Int): Event {
        val response = apiService.getDetailEvent(id)
        return response.event?.toDomain()
            ?: throw Exception("Event not found")
    }
}
