package id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase

import id.my.osa.dicodingfundamentalandroidsubs1.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class IsFavoriteUseCase(private val repository: EventRepository) {
    operator fun invoke(id: Int): Flow<Boolean> {
        return repository.isFavorite(id)
    }
}
