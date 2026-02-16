package id.my.osa.dicodingfundamentalandroidsubs1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.EventResponse
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<EventResponse.ListEventsItem>>()
    val upcomingEvents: LiveData<List<EventResponse.ListEventsItem>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<EventResponse.ListEventsItem>>()
    val finishedEvents: LiveData<List<EventResponse.ListEventsItem>> = _finishedEvents

    private val _bannerEvents = MutableLiveData<List<EventResponse.ListEventsItem>>()
    val bannerEvents: LiveData<List<EventResponse.ListEventsItem>> = _bannerEvents

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
        val client = ApiConfig.getApiService().getEvents( limit = 10)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isBannerLoading.value = false
                if (response.isSuccessful) {
                    _bannerEvents.value = response.body()?.listEvents?.take(5)
                } else {
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isBannerLoading.value = false
                _errorMessage.value = t.message
            }
        })
    }

    private fun findUpcomingEvents() {
        _isUpcomingLoading.value = true
        val client = ApiConfig.getApiService().getEvents(1, limit = 5)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isUpcomingLoading.value = false
                if (response.isSuccessful) {
                    _upcomingEvents.value = response.body()?.listEvents?.take(5)
                } else {
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isUpcomingLoading.value = false
                _errorMessage.value = t.message
            }
        })
    }

    private fun findFinishedEvents() {
        _isFinishedLoading.value = true
        val client = ApiConfig.getApiService().getEvents(0, limit = 5)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isFinishedLoading.value = false
                if (response.isSuccessful) {
                    _finishedEvents.value = response.body()?.listEvents?.take(5)
                } else {
                    _errorMessage.value = response.message()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isFinishedLoading.value = false
                _errorMessage.value = t.message
            }
        })
    }
}

