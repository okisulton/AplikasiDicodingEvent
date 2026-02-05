package id.my.osa.dicodingfundamentalandroidsubs1.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import id.my.osa.dicodingfundamentalandroidsubs1.R
import id.my.osa.dicodingfundamentalandroidsubs1.data.remote.response.DetailEventResponse
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.FragmentDetailBinding
import androidx.core.net.toUri

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    private var eventLink: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupObservers()

        // Fetch event detail using the event ID from navigation args
        viewModel.fetchEventDetail(args.eventId)
    }

    private fun setupToolbar() {

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupObservers() {
        viewModel.eventDetail.observe(viewLifecycleOwner) { event ->
            event?.let { displayEventDetail(it) }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                showToast(message)
            }
        }
    }

    private fun displayEventDetail(event: DetailEventResponse.Event) {
        with(binding) {

            val isShow = true
            var scrollRange = -1

            binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbar.title = viewModel.eventDetail.value?.name
                } else if (isShow) {
                    binding.collapsingToolbar.title = " "
                }
            }

            binding.toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }


            Glide.with(requireContext())
                .load(event.imageLogo)
                .into(ivEventCover)

            tvEventName.text = event.name
            tvEventCategory.text = event.category ?: "Event"

            tvEventOwner.text = event.ownerName
            tvEventTime.text = event.beginTime

            val remainingQuota = (event.quota ?: 0) - (event.registrants ?: 0)
            tvEventQuota.text = remainingQuota.toString()

            when {
                remainingQuota <= 0 -> {
                    tvEventQuota.setTextColor(resources.getColor(android.R.color.holo_red_dark, null))
                    fabRegister.isEnabled = false
                    fabRegister.text = getString(R.string.quota_full)
                }
                remainingQuota < 100 -> {
                    tvEventQuota.setTextColor(resources.getColor(android.R.color.holo_orange_dark, null))
                }
                else -> {
                    tvEventQuota.setTextColor(resources.getColor(android.R.color.holo_green_dark, null))
                }
            }

            tvEventDescription.text = HtmlCompat.fromHtml(
                event.description ?: "No description available",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            eventLink = event.link

            fabRegister.setOnClickListener {
                eventLink?.let { link ->
                    openLinkInBrowser(link)
                } ?: showToast("Registration link not available")
            }
        }
    }

    private fun openLinkInBrowser(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            startActivity(intent)
        } catch (e: Exception) {
            showToast("Unable to open link: ${e.message}")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
