package id.my.osa.dicodingfundamentalandroidsubs1.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    private val autoScrollHandler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private var isAutoScrolling = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBannerSlider()
        setupUpcomingEvents()
        setupFinishedEvents()
        observeViewModel()
    }

    private fun setupBannerSlider() {
        val bannerAdapter = BannerAdapter { event ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(event.id!!)
            findNavController().navigate(action)
        }
        binding.vpUpcomingBanner.adapter = bannerAdapter

        homeViewModel.bannerEvents.observe(viewLifecycleOwner) { events ->
            bannerAdapter.submitList(events)
            if (events.isNotEmpty()) {
                startAutoScroll(events.size)
            }
        }

        homeViewModel.isBannerLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbBanner.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Add page change callback to handle user swipes
        binding.vpUpcomingBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
            }

            @SuppressLint("SwitchIntDef")
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                // Pause auto-scroll when user is swiping
                when (state) {
                    ViewPager2.SCROLL_STATE_DRAGGING -> {
                        isAutoScrolling = false
                        stopAutoScroll()
                    }
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        isAutoScrolling = true
                        startAutoScroll(bannerAdapter.itemCount)
                    }
                }
            }
        })
    }

    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            if (isAutoScrolling && _binding != null) {
                val itemCount = (binding.vpUpcomingBanner.adapter as? BannerAdapter)?.itemCount ?: 0
                if (itemCount > 0) {
                    currentPage = (currentPage + 1) % itemCount
                    binding.vpUpcomingBanner.setCurrentItem(currentPage, true)
                    autoScrollHandler.postDelayed(this, 3000) // 3 seconds
                }
            }
        }
    }

    private fun startAutoScroll(itemCount: Int) {
        if (itemCount > 1) {
            stopAutoScroll()
            autoScrollHandler.postDelayed(autoScrollRunnable, 3000)
        }
    }

    private fun stopAutoScroll() {
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }

    private fun setupUpcomingEvents() {
        val upcomingEventsAdapter = EventAdapter { event ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(event.id!!)
            findNavController().navigate(action)
        }

        binding.rvUpcomingEvents.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingEventsAdapter
        }

        homeViewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            upcomingEventsAdapter.submitList(events)
        }

        homeViewModel.isUpcomingLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbUpcoming.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.rvUpcomingEvents.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun setupFinishedEvents() {
        val finishedEventsAdapter = EventVerticalAdapter { event ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(event.id!!)
            findNavController().navigate(action)
        }

        binding.rvFinishedEvents.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = finishedEventsAdapter
        }

        homeViewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            finishedEventsAdapter.submitList(events)
        }

        homeViewModel.isFinishedLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbFinished.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.rvFinishedEvents.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun observeViewModel() {
        homeViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                showToast(message)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        isAutoScrolling = false
        stopAutoScroll()
    }

    override fun onResume() {
        super.onResume()
        isAutoScrolling = true
        val itemCount = (binding.vpUpcomingBanner.adapter as? BannerAdapter)?.itemCount ?: 0
        if (itemCount > 1) {
            startAutoScroll(itemCount)
        }
    }
}


