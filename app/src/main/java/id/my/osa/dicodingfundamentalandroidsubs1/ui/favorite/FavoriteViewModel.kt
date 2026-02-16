package id.my.osa.dicodingfundamentalandroidsubs1.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.my.osa.dicodingfundamentalandroidsubs1.domain.model.Event
import id.my.osa.dicodingfundamentalandroidsubs1.domain.usecase.GetFavoriteEventsUseCase

class FavoriteViewModel(
    getFavoriteEventsUseCase: GetFavoriteEventsUseCase
) : ViewModel() {

    val favoriteEvents: LiveData<List<Event>> = getFavoriteEventsUseCase().asLiveData()
}
