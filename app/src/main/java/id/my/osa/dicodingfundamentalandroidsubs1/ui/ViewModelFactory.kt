package id.my.osa.dicodingfundamentalandroidsubs1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.my.osa.dicodingfundamentalandroidsubs1.di.ServiceLocator
import id.my.osa.dicodingfundamentalandroidsubs1.ui.detail.DetailViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.ui.finisehd.FinishedViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.ui.home.HomeViewModel
import id.my.osa.dicodingfundamentalandroidsubs1.ui.upcoming.UpcomingViewModel

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(
                    getBannerEventsUseCase = ServiceLocator.provideGetBannerEventsUseCase(),
                    getUpcomingEventsUseCase = ServiceLocator.provideGetUpcomingEventsUseCase(),
                    getFinishedEventsUseCase = ServiceLocator.provideGetFinishedEventsUseCase()
                ) as T
            }
            modelClass.isAssignableFrom(UpcomingViewModel::class.java) -> {
                UpcomingViewModel(
                    getUpcomingEventsUseCase = ServiceLocator.provideGetUpcomingEventsUseCase()
                ) as T
            }
            modelClass.isAssignableFrom(FinishedViewModel::class.java) -> {
                FinishedViewModel(
                    getFinishedEventsUseCase = ServiceLocator.provideGetFinishedEventsUseCase()
                ) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(
                    getEventDetailUseCase = ServiceLocator.provideGetEventDetailUseCase()
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
