package id.my.osa.dicodingfundamentalandroidsubs1.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetUpcomingEventsUseCase
import kotlinx.coroutines.launch

class UpcomingViewModel(
    private val getUpcomingEventsUseCase: GetUpcomingEventsUseCase
) : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<Event>>()
    val upcomingEvents: LiveData<List<Event>> = _upcomingEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        fetchUpcomingEvents()
    }

    fun fetchUpcomingEvents(query: String? = null) {
        _isLoading.value = true
        _errorMessage.value = ""

        viewModelScope.launch {
            try {
                val events = getUpcomingEventsUseCase(query)
                _upcomingEvents.value = events
            } catch (t: Throwable) {
                _errorMessage.value = "Network error: ${t.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
