package id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase

import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteEventsUseCase(private val repository: EventRepository) {
    operator fun invoke(): Flow<List<Event>> {
        return repository.getFavoriteEvents()
    }
}
