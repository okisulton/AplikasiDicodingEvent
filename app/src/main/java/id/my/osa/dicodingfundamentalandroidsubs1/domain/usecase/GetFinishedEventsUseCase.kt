package id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase

import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.repository.EventRepository

class GetFinishedEventsUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(query: String? = null): List<Event> =
        repository.getFinishedEvents(query)
}
