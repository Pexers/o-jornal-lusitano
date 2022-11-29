/*
 * Copyright © 11/29/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.activities.MainActivity
import com.pexers.ojornallusitano.databinding.FragmentCategoriesBinding
import com.pexers.ojornallusitano.utils.Categories
import com.pexers.ojornallusitano.utils.JournalData

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    // Required for search feature
    private var currentCategoryJournals = arrayListOf<JournalData>()

    private var _binding: FragmentCategoriesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        updateCategory(Categories.ALL)
        setupPopupMenu()
        initSearchBar()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupPopupMenu() {
        val popupMenu = PopupMenu(activity, binding.toggleCategoriesPopup)
        popupMenu.inflate(R.menu.menu_categories)
        binding.toggleCategoriesPopup.setOnCheckedChangeListener { _, isChecked -> if (isChecked) popupMenu.show() }
        popupMenu.setOnDismissListener { binding.toggleCategoriesPopup.isChecked = false }
        popupMenu.setOnMenuItemClickListener {
            val categoryClicked =
                Categories.values().find { c -> getString(c.displayName) == it.title.toString() }
            if (categoryClicked == null) false
            else {
                updateCategory(categoryClicked)
                true
            }
        }
    }

    private fun initSearchBar() {
        val mainAct = activity as MainActivity
        binding.toggleSearchCategories.setOnCheckedChangeListener { _, isChecked ->
            binding.searchBar.apply {
                if (isChecked) {
                    visibility = View.VISIBLE
                    requestFocus()
                    startAnimation(AnimationUtils.loadAnimation(context, R.anim.search_bar_in))
                } else {
                    visibility = View.GONE
                    clearFocus()
                    text.clear()
                    startAnimation(AnimationUtils.loadAnimation(context, R.anim.search_bar_out))
                }
            }
        }
        val inputManager =
            mainAct.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        binding.searchBar.apply {
            // Filter current category journals by name
            addTextChangedListener {
                mainAct.updateRecyclerView(
                    mainAct.filterByInput(it.toString(), currentCategoryJournals), true
                )
            }
            // Open input window when search bar is focused
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    inputManager.showSoftInput(
                        binding.searchBar, InputMethodManager.SHOW_IMPLICIT
                    )
                } else inputManager.hideSoftInputFromWindow(binding.searchBar.windowToken, 0)
            }
        }
    }

    private fun updateCategory(category: Categories) {
        binding.textViewCategory.text = getString(category.displayName)
        val mainAct = activity as MainActivity
        currentCategoryJournals = mainAct.filterByCategory(category)
        mainAct.updateRecyclerView(currentCategoryJournals)
    }

}

