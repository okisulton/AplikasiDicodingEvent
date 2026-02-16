package id.my.osa.dicodingfundamentalandroidsubs1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetBannerEventsUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetFinishedEventsUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetUpcomingEventsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getBannerEventsUseCase: GetBannerEventsUseCase,
    private val getUpcomingEventsUseCase: GetUpcomingEventsUseCase,
    private val getFinishedEventsUseCase: GetFinishedEventsUseCase
) : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<Event>>()
    val upcomingEvents: LiveData<List<Event>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<Event>>()
    val finishedEvents: LiveData<List<Event>> = _finishedEvents

    private val _bannerEvents = MutableLiveData<List<Event>>()
    val bannerEvents: LiveData<List<Event>> = _bannerEvents

    private val _isBannerLoading = MutableLiveData<Boolean>()
    val isBannerLoading: LiveData<Boolean> = _isBannerLoading

    private val _isUpcomingLoading = MutableLiveData<Boolean>()
    val isUpcomingLoading: LiveData<Boolean> = _isUpcomingLoading

    private val _isFinishedLoading = MutableLiveData<Boolean>()
    val isFinishedLoading: LiveData<Boolean> = _isFinishedLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        findBannerEvents()
        findUpcomingEvents()
        findFinishedEvents()
    }

    private fun findBannerEvents() {
        _isBannerLoading.value = true
        viewModelScope.launch {
            try {
                val events = getBannerEventsUseCase()
                _bannerEvents.value = events
            } catch (t: Throwable) {
                _errorMessage.value = t.message
            } finally {
                _isBannerLoading.value = false
            }
        }
    }

    private fun findUpcomingEvents() {
        _isUpcomingLoading.value = true
        viewModelScope.launch {
            try {
                val events = getUpcomingEventsUseCase()
                _upcomingEvents.value = events.take(5)
            } catch (t: Throwable) {
                _errorMessage.value = t.message
            } finally {
                _isUpcomingLoading.value = false
            }
        }
    }

    private fun findFinishedEvents() {
        _isFinishedLoading.value = true
        viewModelScope.launch {
            try {
                val events = getFinishedEventsUseCase()
                _finishedEvents.value = events.take(5)
            } catch (t: Throwable) {
                _errorMessage.value = t.message
            } finally {
                _isFinishedLoading.value = false
            }
        }
    }
}
