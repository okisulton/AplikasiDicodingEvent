package id.my.osa.dicodingfundamentalandroidsubs1.ui.finisehd

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
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.FragmentFinishedBinding
import id.my.osa.dicodingfundamentalandroidsubs1.ui.home.EventVerticalAdapter

class FinishedFragment : Fragment() {

    private var _binding: FragmentFinishedBinding? = null
    private val binding get() = _binding

    private val viewModel: FinishedViewModel by viewModels()
    private var eventAdapter: EventVerticalAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedBinding.inflate(inflater, container, false)
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
                FinishedFragmentDirections.actionFinishedFragmentToDetailFragment(event.id!!)
            findNavController().navigate(action)
        }

        binding?.rvFinishedEvents?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = eventAdapter
        }
    }

    private fun setupSearchView() {
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.fetchFinishedEvents(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.fetchFinishedEvents()
                }
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            eventAdapter?.submitList(events)

            if (events.isEmpty() && viewModel.isLoading.value == false) {
                binding?.tvEmpty?.visibility = View.VISIBLE
                binding?.rvFinishedEvents?.visibility = View.GONE
            } else {
                binding?.tvEmpty?.visibility = View.GONE
                binding?.rvFinishedEvents?.visibility = View.VISIBLE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE

            if (isLoading) {
                binding?.rvFinishedEvents?.visibility = View.GONE
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
