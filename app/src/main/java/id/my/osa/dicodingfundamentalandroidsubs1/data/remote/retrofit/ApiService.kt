package id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit

import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.DetailEventResponse
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") active: Int? = null,
        @Query("q") query: String? = null,
        @Query("limit") limit: Int? = null
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(@Path("id") id: Int): Call<DetailEventResponse>
}
