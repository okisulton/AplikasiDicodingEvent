package id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase

import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.repository.EventRepository

class GetEventDetailUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(id: Int): Event =
        repository.getEventDetail(id)
}
