package id.my.osa.dicodingfundamentalandroidsubs1.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import id.my.osa.dicodingfundamentalandroidsubs1.databinding.FragmentFavoriteBinding
import id.my.osa.dicodingfundamentalandroidsubs1.ui.ViewModelFactory
import id.my.osa.dicodingfundamentalandroidsubs1.ui.home.EventVerticalAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root ?: throw IllegalStateException("Binding is not initialized")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = EventVerticalAdapter { event ->
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(event.id)
            findNavController().navigate(action)
        }
        binding?.rvFavoriteEvents?.adapter = adapter

        viewModel.favoriteEvents.observe(viewLifecycleOwner) { events ->
            adapter.submitList(events)
            binding?.tvEmpty?.visibility = if (events.isEmpty()) View.VISIBLE else View.GONE
            binding?.rvFavoriteEvents?.visibility =
                if (events.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
