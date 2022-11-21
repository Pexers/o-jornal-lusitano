/*
 * Copyright © 11/21/2022, Pexers (https://github.com/Pexers)
 */

package com.pexers.ojornallusitano.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.pexers.ojornallusitano.R
import com.pexers.ojornallusitano.utils.JournalData
import com.pexers.ojornallusitano.utils.MainActivityListener
import com.pexers.ojornallusitano.utils.SharedPreferencesData

class FavouritesAdapter(
    var dataSet: ArrayList<JournalData>, val mainActListener: MainActivityListener
) : RecyclerView.Adapter<FavouritesAdapter.ViewHolder>(), JournalsAdapter {

    override fun getData() = dataSet

    override fun setData(data: ArrayList<JournalData>) {
        dataSet = data
        notifyDataSetChanged()
    }

    var isEditing: Boolean = false

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
                mainActListener.switchToWebViewActivity(this@FavouritesAdapter.dataSet[adapterPosition])
            }
            if (isEditing) {
                val removeFavourite =
                    view.findViewById<ImageButton>(R.id.imageButton_removeFavourite)
                val dialogBuilder = AlertDialog.Builder(frameLayout.context)
                removeFavourite.setOnClickListener { dialogBuilder.show() }
                dialogBuilder.apply {
                    setMessage(R.string.remove_favourite_message)
                    setPositiveButton(R.string.yes) { _, _ ->
                        SharedPreferencesData.removeFavourite(this@FavouritesAdapter.dataSet[adapterPosition].name)
                        dataSet.removeAt(adapterPosition)
                        notifyItemRemoved(adapterPosition)  // No need to notify the all dataset
                    }
                    setNegativeButton(R.string.no) { dialog, _ -> dialog.dismiss() }
                    create()
                }
            } else {
                val threeDots = view.findViewById<ImageButton>(R.id.imageButton_threeDots)
                val popupMenu = PopupMenu(frameLayout.context, threeDots)
                popupMenu.inflate(R.menu.menu_three_dots)
                threeDots.setOnClickListener { popupMenu.show() }
                popupMenu.setOnMenuItemClickListener {
                    val journalUrl = this@FavouritesAdapter.dataSet[adapterPosition].url
                    when (it.itemId) {
                        R.id.browse_journal -> {
                            browseUrl(journalUrl)
                            true
                        }
                        R.id.share_journal -> {
                            shareUrl(journalUrl)
                            true
                        }
                        else -> false
                    }
                }
            }
        }
    }

    private fun browseUrl(journalUrl: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(journalUrl)
        mainActListener.startNewActivity(openURL)
    }

    private fun shareUrl(journalUrl: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, journalUrl)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        mainActListener.startNewActivity(shareIntent)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = if (isEditing) LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_favourites_edit, viewGroup, false)
        else LayoutInflater.from(viewGroup.context)
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