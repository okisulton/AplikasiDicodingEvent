package id.my.osa.dicodingfundamentalandroidsubs1.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import id.my.osa.dicodingfundamentalandroidsubs1.R
import id.my.osa.dicodingfundamentalandroidsubs1.data.local.datastore.SettingPreferences
import id.my.osa.dicodingfundamentalandroidsubs1.data.local.datastore.dataStore
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme before setContentView
        val settingPreferences = SettingPreferences.getInstance(dataStore)
        settingPreferences.getThemeSetting().asLiveData().observe(this) { isDarkMode ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)


        ViewCompat.setOnApplyWindowInsetsListener(binding.bottomNav) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(bottom = systemBars.bottom)
            insets
        }

        // Hide the action bar
        supportActionBar?.hide()

        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment_content_main
            ) as NavHostFragment

        NavigationUI.setupWithNavController(
            binding.bottomNav,
            navHostFragment.navController
        )

        // Hide bottom nav on detail screen
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> binding.bottomNav.visibility = android.view.View.GONE
                else -> binding.bottomNav.visibility = android.view.View.VISIBLE
            }
        }
    }
}