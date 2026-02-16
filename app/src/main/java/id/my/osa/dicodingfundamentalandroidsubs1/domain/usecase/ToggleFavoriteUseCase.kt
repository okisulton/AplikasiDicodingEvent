package id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase

import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.repository.EventRepository

class ToggleFavoriteUseCase(private val repository: EventRepository) {
    suspend operator fun invoke(event: Event, isFavorite: Boolean) {
        if (isFavorite) {
            repository.removeFavorite(event.id)
        } else {
            repository.addFavorite(event)
        }
    }
}
