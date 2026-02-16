package id.my.osa.dicodingfundamentalandroidsubs1.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.my.osa.dicodingfundamentalandroidsubs1.di.ServiceLocator
import id.my.osa.dicodingfundamentalandroidsubs1.ui.detail.DetailViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.ui.favorite.FavoriteViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.ui.finisehd.FinishedViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.ui.home.HomeViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.ui.settings.SettingsViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.ui.upcoming.UpcomingViewModel

class ViewModelFactory(
    private val applicationContext: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(
                    getBannerEventsUseCase = ServiceLocator.provideGetBannerEventsUseCase(
                        applicationContext
                    ),
                    getUpcomingEventsUseCase = ServiceLocator.provideGetUpcomingEventsUseCase(
                        applicationContext
                    ),
                    getFinishedEventsUseCase = ServiceLocator.provideGetFinishedEventsUseCase(
                        applicationContext
                    )
                ) as T
            }
            modelClass.isAssignableFrom(UpcomingViewModel::class.java) -> {
                UpcomingViewModel(
                    getUpcomingEventsUseCase = ServiceLocator.provideGetUpcomingEventsUseCase(
                        applicationContext
                    )
                ) as T
            }
            modelClass.isAssignableFrom(FinishedViewModel::class.java) -> {
                FinishedViewModel(
                    getFinishedEventsUseCase = ServiceLocator.provideGetFinishedEventsUseCase(
                        applicationContext
                    )
                ) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(
                    getEventDetailUseCase = ServiceLocator.provideGetEventDetailUseCase(
                        applicationContext
                    ),
                    isFavoriteUseCase = ServiceLocator.provideIsFavoriteUseCase(applicationContext),
                    toggleFavoriteUseCase = ServiceLocator.provideToggleFavoriteUseCase(
                        applicationContext
                    )
                ) as T
            }

            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(
                    getFavoriteEventsUseCase = ServiceLocator.provideGetFavoriteEventsUseCase(
                        applicationContext
                    )
                ) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(
                    settingPreferences = ServiceLocator.provideSettingPreferences(applicationContext)
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        fun getInstance(context: Context): ViewModelFactory {
            return ViewModelFactory(context.applicationContext)
        }
    }
}
