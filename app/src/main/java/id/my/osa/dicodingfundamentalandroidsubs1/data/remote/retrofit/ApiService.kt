package id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit

import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.DetailEventResponse
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.EventResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int? = null,
        @Query("q") query: String? = null,
        @Query("limit") limit: Int? = null
    ): EventResponse

    @GET("events/{id}")
    suspend fun getDetailEvent(@Path("id") id: Int): DetailEventResponse
}
