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
import com.pexers.ojornallusitano.databinding.FragmentRecentBinding

class RecentFragment : Fragment(R.layout.fragment_recent) {

    private var _binding: FragmentRecentBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentBinding.inflate(inflater, container, false)
        setupEmptyRecent()
        return binding.root
    }

    private fun setupEmptyRecent() {
        binding.imageButtonEmptyRecent.setOnClickListener {
            val mainAct = activity as MainActivity
            mainAct.clearRecentQueue()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
