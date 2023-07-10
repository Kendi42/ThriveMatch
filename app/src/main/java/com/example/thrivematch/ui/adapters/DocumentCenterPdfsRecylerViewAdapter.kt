package com.example.thrivematch.ui.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivematch.R
import com.example.thrivematch.data.models.PdfFileModel
import com.example.thrivematch.ui.more_info.DocumentSharingSharedViewModel
import com.example.thrivematch.ui.side_nav_fragments.DocumentUploadFragment
import java.io.File

class DocumentCenterPdfsRecylerViewAdapter (private val itemList: List<PdfFileModel>,
                                            private val context: Context,
                                            private val itemClickListener: DocumentItemClickListener ) :
    RecyclerView.Adapter<DocumentCenterPdfsRecylerViewAdapter.ViewHolder>() {

    // Create the ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPdfName: TextView = itemView.findViewById(R.id.tv_doc_center_pdf_name)
        val ivMoreInfo: ImageView = itemView.findViewById(R.id.iv_doc_center_pdf_more_info)
    }

    // Create the ViewHolder and inflate the item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pdf_item_document_center_rv, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = itemList[position]
        holder.tvPdfName.text = listItem.fileName

        holder.ivMoreInfo.setOnClickListener {view ->
            val popupMenu = PopupMenu(context, view)
            popupMenu.inflate(R.menu.popup_menu_options) // Create a menu resource file (menu_options.xml) with your desired options
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.download_option -> {
                        val file = File(listItem.filePath)
                        if (file.exists()) {
                            // Create an ACTION_VIEW intent to open the file using a PDF viewer
                            val intent = Intent(Intent.ACTION_VIEW)
                            val uri = FileProvider.getUriForFile(context, "com.example.thrivematch.fileprovider", file)
                            intent.setDataAndType(uri, "application/pdf")
                            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                            ContextCompat.startActivity(context, intent, null)
                        } else {
                            Toast.makeText(context, "PDF file not found", Toast.LENGTH_SHORT).show()
                        }
                        true
                    }

                    R.id.delete_option -> {
                        // Handle delete option
                        itemClickListener.onDeleteDocument(position)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()

        }
    }

    override fun getItemCount(): Int {
        return itemList.size

    }


    interface DocumentItemClickListener {
        fun onDeleteDocument(position: Int)
    }



}