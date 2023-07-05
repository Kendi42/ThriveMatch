package com.example.thrivematch.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thrivematch.R
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.google.android.material.card.MaterialCardView

class CardSwipeAdapter(private var cardSwipeItem: List<CardSwipeItemModel>, private val listener:((CardSwipeItemModel)-> Unit)) : RecyclerView.Adapter<CardSwipeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName: TextView = itemView.findViewById(R.id.tv_card_name)
        val tvIndustry: TextView = itemView.findViewById(R.id.tv_card_industry)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_card_description)
        val ivPicture: ImageView = itemView.findViewById(R.id.iv_card_picture)
        val cvCardSwipe: MaterialCardView = itemView.findViewById(R.id.cv_card_swipe)
    }

    fun setCardSwipeItems(newCardSwipeItems: List<CardSwipeItemModel>) {
        cardSwipeItem = newCardSwipeItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_swipe_layout, parent, false)
        return ViewHolder(view)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cardSwipeItem[position]
         holder.tvName.text = item.name
        holder.tvIndustry.text = item.industry
        holder.tvDescription.text = item.description
        holder.cvCardSwipe.setOnClickListener{
            listener.invoke(item)
        }
        Glide.with(holder.itemView).load(item.imageURL).into(holder.ivPicture)
    }

    override fun getItemCount(): Int {
        return cardSwipeItem.size
    }


}