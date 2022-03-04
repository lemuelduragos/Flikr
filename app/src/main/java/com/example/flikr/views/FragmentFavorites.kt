package com.example.flikr.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flikr.adapters.ImageRecyclerViewAdapter
import com.example.flikr.databinding.SharedFragmentBinding
import com.example.flikr.viewmodels.SharedViewModel

class FragmentFavorites : Fragment(), ImageRecyclerViewAdapter.AdapterCallback {
    private lateinit var binding: SharedFragmentBinding
    private lateinit var viewModel: SharedViewModel
    private var adapter: ImageRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SharedFragmentBinding.inflate(layoutInflater)
        initRecyclerView()

        //set up observer for image data
        activity?.application?.let { application ->
            viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
                SharedViewModel::class.java
            )
            viewModel.getImageData().observe(viewLifecycleOwner, {
                adapter?.setDataList(it, true)
                binding.progressCircular.visibility = View.GONE
            })
        }

        binding.progressCircular.visibility = View.VISIBLE

        return binding.root
    }

    private fun initRecyclerView() {
        //set up image adapter for recycler view
        adapter = ImageRecyclerViewAdapter(this)
        binding.imageRecycler.layoutManager = LinearLayoutManager(context)
        binding.imageRecycler.adapter = adapter
    }

    //on click listener from ImageRecyclerViewAdapter
    override fun click(id: String, isFavorite: Boolean) {
        viewModel.setFavorite(id, isFavorite)
    }
}