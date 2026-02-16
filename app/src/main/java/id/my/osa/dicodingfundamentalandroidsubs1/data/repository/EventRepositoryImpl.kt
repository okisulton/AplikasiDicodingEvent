package id.my.osa.dicodingfundamentalandroidsubs1.data.repository

import id.my.osa.dicodingfundamentalandroidsubs1.data.local.dao.FavoriteEventDao
import id.my.osa.dicodingfundamentalandroidsubs1.data.mapper.toDomain
import id.my.osa.dicodingfundamentalandroidsubs1.data.mapper.toEntity
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiService
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventRepositoryImpl(
    private val apiService: ApiService,
    private val favoriteEventDao: FavoriteEventDao
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

    // Favorite operations
    override fun getFavoriteEvents(): Flow<List<Event>> {
        return favoriteEventDao.getAllFavorites().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun isFavorite(id: Int): Flow<Boolean> {
        return favoriteEventDao.isFavorite(id)
    }

    override suspend fun addFavorite(event: Event) {
        favoriteEventDao.insert(event.toEntity())
    }

    override suspend fun removeFavorite(id: Int) {
        favoriteEventDao.deleteById(id)
    }
}
