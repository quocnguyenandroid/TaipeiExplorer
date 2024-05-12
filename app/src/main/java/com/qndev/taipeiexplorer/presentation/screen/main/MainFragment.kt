package com.qndev.taipeiexplorer.presentation.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qndev.taipeiexplorer.R
import com.qndev.taipeiexplorer.databinding.FragmentMainBinding
import com.qndev.taipeiexplorer.presentation.adapter.LocationItemAdapter
import com.qndev.taipeiexplorer.presentation.adapter.OnLocationClickListener
import com.qndev.taipeiexplorer.presentation.dialog.ChangeLanguageDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), OnLocationClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var locationItemAdapter: LocationItemAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setUpListeners()
        observeData()

        viewModel.getLocations()
    }

    private fun setUpListeners() {
        // Set up the swipe-to-refresh listener
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.resetLocationListState()
            viewModel.getLocations()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.languageButton.setOnClickListener {
            ChangeLanguageDialogFragment.show(parentFragmentManager) { language ->
                viewModel.resetLocationListState()
                viewModel.updateLanguage(language)
                viewModel.getLocations()
            }
        }

    }

    private fun setupRecyclerView() {
        locationItemAdapter = LocationItemAdapter()
        val layoutManager =
            LinearLayoutManager(this@MainFragment.context, LinearLayoutManager.VERTICAL, false)
        locationItemAdapter.setOnLocationClickListener(this)
        binding.locationList.layoutManager = layoutManager
        binding.locationList.adapter = locationItemAdapter

        // Add scroll listener to detect when the user reaches the end of the list
        binding.locationList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
                    // Load more data
                    if (!viewModel.locationListState.value.isLoading) {
                        viewModel.getLocations()
                    }
                }
            }
        })
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.locationListState.distinctUntilChangedBy { it.locationList }.collectLatest {
                locationItemAdapter.updateLocationList(it.locationList)
            }
        }

        // Update the progress bar and error text visibility based on the state
        lifecycleScope.launch {
            viewModel.locationListState.collectLatest {
                if (it.isLoading && it.locationList.isEmpty()) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.errorText.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.locationList.visibility = View.VISIBLE
                }

                if (it.errorMassage.isNotBlank()) {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = it.errorMassage
                }
            }
        }
    }

    override fun onLocationClick(position: Int) {
        val locationItem = viewModel.locationListState.value.locationList[position]
        val bundle = bundleOf(
            "name" to locationItem.name,
            "images" to locationItem.images.toTypedArray(),
            "introduction" to locationItem.introduction,
            "address" to locationItem.address,
            "tel" to locationItem.tel,
            "lat" to locationItem.nlat.toFloat(),
            "lng" to locationItem.elong.toFloat(),
            "url" to locationItem.url
        )
        findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
    }
}