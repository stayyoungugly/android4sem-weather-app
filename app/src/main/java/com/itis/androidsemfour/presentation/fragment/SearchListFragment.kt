package com.itis.androidsemfour.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.itis.androidsemfour.presentation.adapter.CityAdapter
import com.itis.androidsemfour.R
import com.itis.androidsemfour.databinding.FragmentListBinding
import com.itis.androidsemfour.di.DIContainer
import com.itis.androidsemfour.presentation.fragment.viewmodel.SearchListFragmentViewModel
import com.itis.androidsemfour.utils.ViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

private const val CNT_10 = 10
private const val DEFAULT_LAT = 51.59
private const val DEFAULT_LON = 45.96
private const val REQUEST_CODE_100 = 100

class SearchListFragment : Fragment(R.layout.fragment_list) {
    val bundle = Bundle()
    private var userLatitude: Double = DEFAULT_LAT
    private var userLongitude: Double = DEFAULT_LON
    private lateinit var viewModel: SearchListFragmentViewModel
    private lateinit var userLocation: FusedLocationProviderClient
    private lateinit var binding: FragmentListBinding
    private lateinit var cityAdapter: CityAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        initObjects()
        initObservers()
        createLocationList()
        startCitySearch()
    }

    private fun createCityRecyclerView() {
        lifecycleScope.launch {
            viewModel.getCityList(userLatitude, userLongitude, CNT_10)
        }
    }

    private fun createLocationList() {
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
            requestPermissions(permissions, REQUEST_CODE_100)
        } else {
            userLocation = LocationServices.getFusedLocationProviderClient(requireActivity())
            userLocation.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    userLongitude = location.longitude
                    userLatitude = location.latitude
                    Snackbar.make(binding.root, "Location was found", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    Snackbar.make(binding.root, "Location was not found", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
        createCityRecyclerView()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_100 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createLocationList()
                } else {
                    Snackbar.make(
                        binding.root,
                        "Geolocation access denied",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun startCitySearch() {
        binding.svCities.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(cityName: String): Boolean {
                lifecycleScope.launch {
                    viewModel.getWeatherByName(cityName)
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })
    }

    private fun initObservers() {
        viewModel.cityList.observe(viewLifecycleOwner) { list ->
            list.fold(onSuccess = { listOfCity ->
                cityAdapter = CityAdapter(
                    listOfCity
                ) {
                    bundle.putInt("id", it)
                    findNavController().navigate(
                        R.id.action_searchFragment_to_detailsFragment,
                        bundle
                    )
                }
                binding.rvCities.adapter = cityAdapter
            }, onFailure = {
                Snackbar.make(
                    binding.root,
                    "City Not Found",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            )
        }

        viewModel.weather.observe(viewLifecycleOwner) { city ->
            city.fold(onSuccess = {
                bundle.putInt("id", it.id)
                findNavController().navigate(
                    R.id.action_searchFragment_to_detailsFragment,
                    bundle
                )
            }, onFailure = {
                Snackbar.make(
                    binding.root,
                    "City Not Found",
                    Snackbar.LENGTH_LONG
                ).show()
            })
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Timber.e(it.message.toString())
        }
    }

    private fun initObjects() {
        val factory = ViewModelFactory(DIContainer)
        viewModel = ViewModelProvider(
            this,
            factory
        )[SearchListFragmentViewModel::class.java]
    }
}
