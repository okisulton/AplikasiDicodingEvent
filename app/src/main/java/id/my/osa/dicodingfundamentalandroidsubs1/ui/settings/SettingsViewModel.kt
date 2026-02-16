package id.my.osa.dicodingfundamentalandroidsubs1.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.my.osa.dicodingfundamentalandroidsubs1.data.local.datastore.SettingPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingPreferences: SettingPreferences
) : ViewModel() {

    val themeSetting: LiveData<Boolean> = settingPreferences.getThemeSetting().asLiveData()

    val reminderSetting: LiveData<Boolean> = settingPreferences.getReminderSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingPreferences.saveThemeSetting(isDarkModeActive)
        }
    }

    fun saveReminderSetting(isReminderActive: Boolean) {
        viewModelScope.launch {
            settingPreferences.saveReminderSetting(isReminderActive)
        }
    }
}
