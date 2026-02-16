package id.my.osa.dicodingfundamentalandroidsubs1.di

import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiConfig
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiService
import id.my.osa.dicodingfundamentalandroidsubs1.data.repository.EventRepositoryImpl
import id.my.osa.dicodingfundamentalandroidsubs1.domain.repository.EventRepository
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetBannerEventsUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetEventDetailUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetFinishedEventsUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetUpcomingEventsUseCase

object ServiceLocator {

    private fun provideApiService(): ApiService = ApiConfig.getApiService()

    private fun provideEventRepository(): EventRepository =
        EventRepositoryImpl(provideApiService())

    fun provideGetUpcomingEventsUseCase(): GetUpcomingEventsUseCase =
        GetUpcomingEventsUseCase(provideEventRepository())

    fun provideGetFinishedEventsUseCase(): GetFinishedEventsUseCase =
        GetFinishedEventsUseCase(provideEventRepository())

    fun provideGetBannerEventsUseCase(): GetBannerEventsUseCase =
        GetBannerEventsUseCase(provideEventRepository())

    fun provideGetEventDetailUseCase(): GetEventDetailUseCase =
        GetEventDetailUseCase(provideEventRepository())
}
