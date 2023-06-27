package com.example.thrivematch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thrivematch.data.PendingMatchModel

class PendingMatchRecyclerViewAdapter(private val itemList: List<PendingMatchModel>) :
    RecyclerView.Adapter<PendingMatchRecyclerViewAdapter.ViewHolder>() {

    // Create the ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val tvName: TextView = itemView.findViewById(R.id.tv_pending_match_name)
         val ivPhoto: ImageView = itemView.findViewById(R.id.iv_pending_match_image)
    }

    // Create the ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pending_match_item, parent, false)
        return ViewHolder(view)
    }

    // Bind the data from the data model to the views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
         holder.tvName.text = item.name
        Glide.with(holder.itemView).load(item.imageURL).into(holder.ivPhoto)
    }


    // Return the number of items in the list
    override fun getItemCount(): Int {
        return itemList.size
    }
}
