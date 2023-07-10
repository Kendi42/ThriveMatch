package com.example.thrivematch.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thrivematch.R
import com.example.thrivematch.data.models.PdfFileModel
import com.example.thrivematch.data.models.PendingMatchModel
import java.io.File

class PdfDownloadRecyclerViewAdapter (private val itemList: List<PdfFileModel>, private val context: Context) :
    RecyclerView.Adapter<PdfDownloadRecyclerViewAdapter.ViewHolder>() {

    // Create the ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPdfName: TextView = itemView.findViewById(R.id.tv_pdf_name)
        val ivDownload: ImageView = itemView.findViewById(R.id.iv_pdf_download)
    }

    // Create the ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pdf_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.tvPdfName.text =item.fileName

        holder.ivDownload.setOnClickListener {
            val file = File(item.filePath)

            if (file.exists()) {
                // Create an ACTION_VIEW intent to open the file using a PDF viewer
                val intent = Intent(Intent.ACTION_VIEW)
                val uri = FileProvider.getUriForFile(context, "com.example.thrivematch.fileprovider", file)
                intent.setDataAndType(uri, "application/pdf")
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                startActivity(context, intent, null)
            } else {
                Toast.makeText(context, "PDF file not found", Toast.LENGTH_SHORT).show()
            }
        }



        }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
