package id.my.osa.dicodingfundamentalandroidsubs1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetEventDetailUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.IsFavoriteUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getEventDetailUseCase: GetEventDetailUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _eventDetail = MutableLiveData<Event?>()
    val eventDetail: LiveData<Event?> = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _eventId = MutableLiveData<Int>()

    val isFavorite: LiveData<Boolean> = _eventId.switchMap { id ->
        isFavoriteUseCase(id).asLiveData()
    }

    fun fetchEventDetail(eventId: Int) {
        _isLoading.value = true
        _eventId.value = eventId
        viewModelScope.launch {
            try {
                val event = getEventDetailUseCase(eventId)
                _eventDetail.value = event
            } catch (t: Throwable) {
                _errorMessage.value = "Failed to load event details: ${t.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite() {
        val event = _eventDetail.value ?: return
        val currentFavorite = isFavorite.value ?: false
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(event, currentFavorite)
            } catch (t: Throwable) {
                _errorMessage.value = "Failed to update favorite: ${t.message}"
            }
        }
    }
}
