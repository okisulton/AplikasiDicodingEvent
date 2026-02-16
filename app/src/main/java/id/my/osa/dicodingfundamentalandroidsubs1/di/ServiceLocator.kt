package id.my.osa.dicodingfundamentalandroidsubs1.di

import android.content.Context
import id.my.osa.dicodingfundamentalandroidsubs1.data.local.database.AppDatabase
import id.my.osa.dicodingfundamentalandroidsubs1.data.local.datastore.SettingPreferences
import id.my.osa.dicodingfundamentalandroidsubs1.data.local.datastore.dataStore
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiConfig
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.retrofit.ApiService
import id.my.osa.dicodingfundamentalandroidsubs1.data.repository.EventRepositoryImpl
import id.my.osa.dicodingfundamentalandroidsubs1.domain.repository.EventRepository
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetBannerEventsUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetEventDetailUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetFavoriteEventsUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetFinishedEventsUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetUpcomingEventsUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.IsFavoriteUseCase
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.ToggleFavoriteUseCase

object ServiceLocator {

    private fun provideApiService(): ApiService = ApiConfig.getApiService()

    private fun provideDatabase(context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    private fun provideEventRepository(context: Context): EventRepository =
        EventRepositoryImpl(provideApiService(), provideDatabase(context).favoriteEventDao())

    fun provideGetUpcomingEventsUseCase(context: Context): GetUpcomingEventsUseCase =
        GetUpcomingEventsUseCase(provideEventRepository(context))

    fun provideGetFinishedEventsUseCase(context: Context): GetFinishedEventsUseCase =
        GetFinishedEventsUseCase(provideEventRepository(context))

    fun provideGetBannerEventsUseCase(context: Context): GetBannerEventsUseCase =
        GetBannerEventsUseCase(provideEventRepository(context))

    fun provideGetEventDetailUseCase(context: Context): GetEventDetailUseCase =
        GetEventDetailUseCase(provideEventRepository(context))

    fun provideGetFavoriteEventsUseCase(context: Context): GetFavoriteEventsUseCase =
        GetFavoriteEventsUseCase(provideEventRepository(context))

    fun provideIsFavoriteUseCase(context: Context): IsFavoriteUseCase =
        IsFavoriteUseCase(provideEventRepository(context))

    fun provideToggleFavoriteUseCase(context: Context): ToggleFavoriteUseCase =
        ToggleFavoriteUseCase(provideEventRepository(context))

    fun provideSettingPreferences(context: Context): SettingPreferences =
        SettingPreferences.getInstance(context.dataStore)
}
