/*
 * Copyright © 11/29/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.activities.MainActivity
import com.pexers.ojornallusitano.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private var _binding: FragmentFavouritesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        setupEditFavourites()
        return binding.root
    }

    private fun setupEditFavourites() {
        val mainAct = activity as MainActivity
        binding.toggleEditFavourites.setOnCheckedChangeListener { _, isChecked ->
            mainAct.setFavouritesEditState(isChecked)
            if (!isChecked) {
                mainAct.checkEmptyRecyclerView()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}