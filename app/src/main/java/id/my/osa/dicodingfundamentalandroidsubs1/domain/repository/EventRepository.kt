package id.my.osa.dicodingfundamentalandroidsubs1.domain.repository

import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    suspend fun getUpcomingEvents(query: String? = null): List<Event>
    suspend fun getFinishedEvents(query: String? = null): List<Event>
    suspend fun getBannerEvents(): List<Event>
    suspend fun getEventDetail(id: Int): Event

    // Favorite operations
    fun getFavoriteEvents(): Flow<List<Event>>
    fun isFavorite(id: Int): Flow<Boolean>
    suspend fun addFavorite(event: Event)
    suspend fun removeFavorite(id: Int)
}
