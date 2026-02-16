package id.my.osa.dicodingfundamentalandroidsubs1.ui.finisehd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.EventResponse
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishedViewModel : ViewModel() {

    private val _finishedEvents = MutableLiveData<List<EventResponse.ListEventsItem>>()
    val finishedEvents: LiveData<List<EventResponse.ListEventsItem>> = _finishedEvents

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

        val client = if (query.isNullOrEmpty()) {
            ApiConfig.getApiService().getEvents(active = 0)
        } else {
            ApiConfig.getApiService().getEvents(active = 0, query = query)
        }

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()

                    _finishedEvents.value = if (!query.isNullOrEmpty()) {
                        events.filter { event ->
                            event.name?.contains(query, ignoreCase = true) == true ||
                            event.ownerName?.contains(query, ignoreCase = true) == true
                        }
                    } else {
                        events
                    }
                } else {
                    _errorMessage.value = "Failed to load events: ${response.message()}"
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Network error: ${t.message}"
            }
        })
    }
}

