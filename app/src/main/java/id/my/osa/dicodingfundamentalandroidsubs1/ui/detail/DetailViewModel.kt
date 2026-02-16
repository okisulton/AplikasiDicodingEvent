package id.my.osa.dicodingfundamentalandroidsubs1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetEventDetailUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getEventDetailUseCase: GetEventDetailUseCase
) : ViewModel() {

    private val _eventDetail = MutableLiveData<Event?>()
    val eventDetail: LiveData<Event?> = _eventDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchEventDetail(eventId: Int) {
        _isLoading.value = true
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
}
