package id.my.osa.dicodingfundamentalandroidsubs1.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.FragmentUpcomingBinding
import id.my.osa.dicodingfundamentalandroidsubs1.ui.home.EventVerticalAdapter

class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding

    private val viewModel: UpcomingViewModel by viewModels()
    private var eventAdapter: EventVerticalAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding?.root ?: throw IllegalStateException("Binding is not initialized")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        eventAdapter = EventVerticalAdapter { event ->
            val action =
                UpcomingFragmentDirections.actionUpcomingFragmentToDetailFragment(event.id!!)
            findNavController().navigate(action)

        }

        binding?.rvUpcomingEvents?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }
    }

    private fun setupSearchView() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.fetchUpcomingEvents(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.fetchUpcomingEvents()
                }
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            eventAdapter?.submitList(events)

            // Show/hide empty state
            if (events.isEmpty() && viewModel.isLoading.value == false) {
                binding?.tvEmpty?.visibility = View.VISIBLE
                binding?.rvUpcomingEvents?.visibility = View.GONE
            } else {
                binding?.tvEmpty?.visibility = View.GONE
                binding?.rvUpcomingEvents?.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE

            // Hide RecyclerView and empty state while loading
            if (isLoading) {
                binding?.rvUpcomingEvents?.visibility = View.GONE
                binding?.tvEmpty?.visibility = View.GONE
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        eventAdapter = null
        _binding = null
    }
}


