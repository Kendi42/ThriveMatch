package com.example.thrivematch.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thrivematch.R
import com.example.thrivematch.data.models.PendingMatchModel

class PdfDownloadRecyclerViewAdapter (private val itemList: List<String>) :
    RecyclerView.Adapter<PdfDownloadRecyclerViewAdapter.ViewHolder>() {

    // Create the ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPdfName: TextView = itemView.findViewById(R.id.tv_pdf_name)
    }

    // Create the ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pdf_item, parent, false)
        return ViewHolder(view)
    }

    // Bind the data from the data model to the views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvPdfName.text = item
    }


    override fun getItemCount(): Int {
        return itemList.size
    }
}
