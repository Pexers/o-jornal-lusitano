/*
 * Copyright © 11/17/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.activities.MainActivity
import com.pexers.ojornallusitano.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment(R.layout.fragment_categories) {
    private var _binding: FragmentCategoriesBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        updateCategory(Categories.ALL)
        initPopupMenu()
        initSearchBar()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initPopupMenu() {
        val popupMenu = PopupMenu(activity, binding.toggleCategories)
        popupMenu.inflate(R.menu.menu_categories)
        binding.toggleCategories.setOnCheckedChangeListener { _, isChecked -> if (isChecked) popupMenu.show() }
        popupMenu.setOnDismissListener { binding.toggleCategories.isChecked = false }
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_all -> updateCategory(Categories.ALL)
                R.id.item_economy -> updateCategory(Categories.ECONOMY_POLITICS)
                R.id.item_general -> updateCategory(Categories.GENERAL)
                R.id.item_fashion -> updateCategory(Categories.FASHION)
                R.id.item_sports -> updateCategory(Categories.SPORTS)
                R.id.item_technology -> updateCategory(Categories.TECHNOLOGY)
            }
            true
        }
    }

    private fun initSearchBar() {
        binding.toggleSearch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.editTextSearchBar.apply {
                    visibility = View.VISIBLE
                    requestFocus()
                    startAnimation(
                        AnimationUtils.loadAnimation(context, R.anim.search_bar_in)
                    )
                }
            } else {
                binding.editTextSearchBar.apply {
                    visibility = View.GONE
                    clearFocus()
                    text.clear()
                    startAnimation(
                        AnimationUtils.loadAnimation(context, R.anim.search_bar_out)
                    )
                }
            }
        }
    }

    fun updateCategory(cat: Categories) {
        binding.textCategory.text = getString(cat.catId)
    }

}

// @formatter:off
enum class Categories(val catId: Int) {
    ALL(R.string.category_all),
    ECONOMY_POLITICS(R.string.category_economy_politics),
    FASHION(R.string.category_fashion),
    GENERAL(R.string.category_general),
    SPORTS(R.string.category_sports),
    TECHNOLOGY(R.string.category_technology)
}
// @formatter:on
