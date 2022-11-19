/*
 * Copyright © 11/18/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.utils.JournalData

class FavouritesAdapter(private var dataSet: ArrayList<JournalData>) :
    RecyclerView.Adapter<FavouritesAdapter.ViewHolder>(),JournalsAdapter {

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

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.textView_favouriteJournal)
            val frameLayout = view.findViewById<FrameLayout>(R.id.frameLayout_favouriteJournal)
            frameLayout.setOnClickListener {
                val journal = this@FavouritesAdapter.dataSet[adapterPosition]
                // TODO: open webpage using URL
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_favourites, viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val journalName = dataSet[position].name
        viewHolder.textView.text = journalName
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}