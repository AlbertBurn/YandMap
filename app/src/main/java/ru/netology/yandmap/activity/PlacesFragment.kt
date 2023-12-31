package ru.netology.yandmap.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import ru.netology.yandmap.R
import ru.netology.yandmap.adapter.Listener
import ru.netology.yandmap.viewmodel.MapViewModel
import ru.netology.yandmap.dto.Place
import ru.netology.yandmap.adapter.PlacesAdapter
import ru.netology.yandmap.databinding.PlacesFragmentBinding

class PlacesFragment : Fragment() {

    private val viewModel: MapViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = PlacesFragmentBinding.inflate(inflater, container, false)

        val viewModel by viewModels<MapViewModel>()

        val adapter = PlacesAdapter(object : Listener {

            override fun onClick(place: Place) {
                findNavController().navigate(
                    R.id.action_placesFragment_to_mapFragment, bundleOf(
                        MapFragment.LAT_KEY to place.lat,
                        MapFragment.LONG_KEY to place.long
                    )
                )
            }

            override fun onDelete(place: Place) {
                viewModel.deleteById(place.id)
            }

            override fun onEdit(place: Place) {
                AddPlaceDialog.newInstance(lat = place.lat, long = place.long, id = place.id)
                    .show(childFragmentManager, null)
            }
        })

        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.data.observe(viewLifecycleOwner) { places ->
                adapter.submitList(places)
                binding.empty.isVisible = places.isEmpty()
            }
        }

        return binding.root
    }
}