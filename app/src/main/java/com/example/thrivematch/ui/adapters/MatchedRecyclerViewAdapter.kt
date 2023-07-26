package com.example.thrivematch.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thrivematch.R
import com.example.thrivematch.data.models.MatchedModel


class MatchedRecyclerViewAdapter(private var itemList: ArrayList<MatchedModel>, private val context: Context, private val listener: MatchedRecyclerViewAdapter.OnItemClickListener):
    RecyclerView.Adapter<MatchedRecyclerViewAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: MatchedModel)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_pending_match_name)
        val ivPhoto: ImageView = itemView.findViewById(R.id.iv_pending_match_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pending_match_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvName.text = item.name
        Glide.with(holder.itemView).load(item.imageURL).into(holder.ivPhoto)
        val phoneNumber= item.phoneNumber

        holder.itemView.setOnClickListener {
            Log.i("Phone number is", phoneNumber)
            openWhatsAppChat(phoneNumber)


        }
    }

     fun openWhatsAppChat(phoneNumber: String) {
        val packageManager = context.packageManager
        val i = Intent(Intent.ACTION_VIEW)
        try {
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
            Log.d("WhatsApp URL", url)
            i.setPackage("com.whatsapp")
            i.data = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber&text=")
            context!!.startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
            val uri = Uri.parse("market://details?id=com.whatsapp")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(goToMarket)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    fun setFilteredList(filteredList: ArrayList<MatchedModel>) {
        this.itemList = filteredList
        notifyDataSetChanged()

    }

}