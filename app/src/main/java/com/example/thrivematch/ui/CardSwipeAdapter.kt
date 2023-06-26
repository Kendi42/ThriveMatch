package com.example.thrivematch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thrivematch.R
import com.example.thrivematch.data.CardSwipeItem

class CardSwipeAdapter(private val cardSwipeItem: List<CardSwipeItem>) : RecyclerView.Adapter<CardSwipeAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv_name: TextView = itemView.findViewById(R.id.tv_card_name)
        val tv_industry: TextView = itemView.findViewById(R.id.tv_card_industry)
        val tv_description: TextView = itemView.findViewById(R.id.tv_card_description)
        val iv_picture: ImageView = itemView.findViewById(R.id.iv_card_picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_card_stack_layout, parent, false)
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cardSwipeItem[position]
         holder.tv_name.text = item.name
        holder.tv_industry.text = item.industry
        holder.tv_description.text = item.description
        Glide.with(holder.itemView).load(item.imageURL).into(holder.iv_picture)
    }

    override fun getItemCount(): Int {
        return cardSwipeItem.size
    }


}