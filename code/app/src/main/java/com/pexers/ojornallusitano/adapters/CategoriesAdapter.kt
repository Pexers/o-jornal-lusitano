/*
 * Copyright © 11/18/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.utils.JournalData
import com.pexers.ojornallusitano.utils.MyListener
import com.pexers.ojornallusitano.utils.SharedPreferencesData

class CategoriesAdapter(var dataSet: ArrayList<JournalData>, val mainActListener: MyListener) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(), JournalsAdapter {

    override fun setData(data: ArrayList<JournalData>) {
        dataSet = data
        notifyDataSetChanged()
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val starToggle: ToggleButton

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.textView_categoryJournal)
            val frameLayout = view.findViewById<FrameLayout>(R.id.frameLayout_categoryJournal)
            frameLayout.setOnClickListener {
                mainActListener.switchToWebViewActivity(this@CategoriesAdapter.dataSet[adapterPosition])
            }
            starToggle = view.findViewById(R.id.toggle_categoryJournalStar)
            starToggle.setOnCheckedChangeListener { _, isChecked ->
                val journal = this@CategoriesAdapter.dataSet[adapterPosition]
                if (isChecked) SharedPreferencesData.addFavourite(journal.name)
                else SharedPreferencesData.removeFavourite(journal.name)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_categories, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val journalName = dataSet[position].name
        viewHolder.textView.text = journalName
        viewHolder.starToggle.isChecked = SharedPreferencesData.favourites!!.contains(journalName)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}