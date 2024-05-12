package com.qndev.taipeiexplorer.presentation.screen.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.qndev.taipeiexplorer.R
import com.qndev.taipeiexplorer.data.remote.response.ImageData
import com.qndev.taipeiexplorer.databinding.FragmentDetailBinding
import com.qndev.taipeiexplorer.presentation.adapter.ImageItemAdapter
import com.qndev.taipeiexplorer.presentation.adapter.OnImageClickListener

class DetailFragment : Fragment(), OnImageClickListener {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var imageList: List<ImageData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetailFragmentArgs by navArgs()
        val name = args.name
        val introduction = args.introduction
        imageList = args.images.toList()
        val address = args.address
        val tel = args.tel
        val latitude = args.lat
        val longitude = args.lng
        val url = args.url

        binding.locationImage.load(imageList[0].src) {
            crossfade(true)
            error(R.drawable.img_placeholder)
            placeholder(R.drawable.img_placeholder)
        }

        // Set up recycler view of image list
        val adapter = ImageItemAdapter(imageList)
        adapter.setOnLocationClickListener(this)
        binding.locationImageList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.locationImageList.adapter = adapter

        binding.locationName.text = name
        binding.locationIntroduction.text = introduction
        binding.locationAddress.text = address
        binding.phoneNumber.text = tel
        binding.urlText.text = url

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.urlText.setOnClickListener {
            findNavController().navigate(
                R.id.action_detailFragment_to_webViewFragment,
                bundleOf("url" to url)
            )
        }

        binding.locationAddress.setOnClickListener {
            // Add geo and zoom level to uri
            val uri = "geo:$latitude,$longitude?z=25"
            val googleMapPackage = "com.google.android.apps.maps"
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            mapIntent.setPackage(googleMapPackage)

            if (mapIntent.resolveActivity(requireContext().packageManager) != null) {
                // Google Maps app is installed, open the location in the app
                startActivity(mapIntent)
            }
        }

        // Dial phone number when click
        binding.phoneNumber.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$tel")
            }
            startActivity(dialIntent)
        }
    }

    override fun onImageClick(position: Int) {
        val imageItem = imageList[position].src
        binding.locationImage.load(imageItem) {
            crossfade(true)
            error(R.drawable.img_placeholder)
            placeholder(R.drawable.img_placeholder)
        }
    }
}