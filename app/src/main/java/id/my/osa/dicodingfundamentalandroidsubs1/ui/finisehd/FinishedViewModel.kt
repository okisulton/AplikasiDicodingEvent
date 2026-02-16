package id.my.osa.dicodingfundamentalandroidsubs1.ui.finisehd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetFinishedEventsUseCase
import kotlinx.coroutines.launch

class FinishedViewModel(
    private val getFinishedEventsUseCase: GetFinishedEventsUseCase
) : ViewModel() {

    private val _finishedEvents = MutableLiveData<List<Event>>()
    val finishedEvents: LiveData<List<Event>> = _finishedEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        fetchFinishedEvents()
    }

    fun fetchFinishedEvents(query: String? = null) {
        _isLoading.value = true
        _errorMessage.value = ""

        viewModelScope.launch {
            try {
                val events = getFinishedEventsUseCase(query)
                _finishedEvents.value = events
            } catch (t: Throwable) {
                _errorMessage.value = "Network error: ${t.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
