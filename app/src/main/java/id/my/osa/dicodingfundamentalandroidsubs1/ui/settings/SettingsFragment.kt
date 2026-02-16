package id.my.osa.dicodingfundamentalandroidsubs1.ui.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import id.my.osa.dicodingfundamentalandroidsubs1.data.worker.ReminderWorker
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.FragmentSettingsBinding
import id.my.osa.dicodingfundamentalandroidsubs1.ui.ViewModelFactory
import java.util.concurrent.TimeUnit

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding

    private val viewModel: SettingsViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                Toast.makeText(
                    requireContext(),
                    "Notification permission is required for daily reminders",
                    Toast.LENGTH_SHORT
                ).show()
                binding?.switchReminder?.isChecked = false
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root ?: throw IllegalStateException("Binding is not initialized")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupThemeSwitch()
        setupReminderSwitch()
    }

    private fun setupThemeSwitch() {
        viewModel.themeSetting.observe(viewLifecycleOwner) { isDarkMode ->
            binding?.switchTheme?.isChecked = isDarkMode
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        binding?.switchTheme?.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSetting(isChecked)
        }
    }

    private fun setupReminderSwitch() {
        viewModel.reminderSetting.observe(viewLifecycleOwner) { isReminderActive ->
            binding?.switchReminder?.isChecked = isReminderActive
            if (isReminderActive) {
                scheduleReminder()
            } else {
                cancelReminder()
            }
        }

        binding?.switchReminder?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        return@setOnCheckedChangeListener
                    }
                }
            }
            viewModel.saveReminderSetting(isChecked)
        }
    }

    private fun scheduleReminder() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<ReminderWorker>(
            1, TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            REMINDER_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )
    }

    private fun cancelReminder() {
        WorkManager.getInstance(requireContext()).cancelUniqueWork(REMINDER_WORK_NAME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REMINDER_WORK_NAME = "daily_event_reminder"
    }
}
