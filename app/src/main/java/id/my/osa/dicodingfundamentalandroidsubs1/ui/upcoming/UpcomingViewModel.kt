package id.my.osa.dicodingfundamentalandroidsubs1.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.EventResponse
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : ViewModel() {

    private val _upcomingEvents = MutableLiveData<List<EventResponse.ListEventsItem>>()
    val upcomingEvents: LiveData<List<EventResponse.ListEventsItem>> = _upcomingEvents

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

        val client = if (query.isNullOrEmpty()) {
            ApiConfig.getApiService().getEvents(active = 1)
        } else {
            ApiConfig.getApiService().getEvents(active = 1, query = query)
        }

        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents ?: emptyList()
                    // Filter upcoming events if searching all events
                    _upcomingEvents.value = if (!query.isNullOrEmpty()) {
                        events.filter { event ->
                            // You can add additional filtering logic here if needed
                            event.name?.contains(query, ignoreCase = true) == true ||
                            event.ownerName?.contains(query, ignoreCase = true) == true
                        }
                    } else {
                        events
                    }
                } else {
                    _errorMessage.value = "Failed to load events: ${response.message()}"
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = "Network error: ${t.message}"
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "UpcomingViewModel"
    }
}

