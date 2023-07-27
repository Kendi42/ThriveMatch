package com.example.thrivematch.ui.side_nav_fragments

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thrivematch.R
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.PdfFileModel
import com.example.thrivematch.databinding.FragmentDocumentUploadBinding
import com.example.thrivematch.databinding.FragmentMoreInfoBinding
import com.example.thrivematch.ui.account_setup.SharedAccountSetupViewModel
import com.example.thrivematch.ui.adapters.DocumentCenterPdfsRecylerViewAdapter
import com.example.thrivematch.ui.adapters.PdfDownloadRecyclerViewAdapter
import com.example.thrivematch.ui.home.HomeViewModel
import com.example.thrivematch.ui.more_info.DocumentSharingSharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class DocumentUploadFragment : Fragment(R.layout.fragment_document_upload),
    DocumentCenterPdfsRecylerViewAdapter.DocumentItemClickListener {
    private lateinit var binding: FragmentDocumentUploadBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DocumentCenterPdfsRecylerViewAdapter
    private var documentNames: LinkedList<String> = LinkedList()
    private var fileDetails: LinkedList<PdfFileModel> = LinkedList()
    private val sharedViewModel: DocumentSharingSharedViewModel by activityViewModels()

    private lateinit var pdfUri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentDocumentUploadBinding.bind(view)
        fileDetails= sharedViewModel.getDocumentList()

        recyclerView = binding.recyclerView
        adapter = DocumentCenterPdfsRecylerViewAdapter(fileDetails, requireActivity(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.btnDocUpload.setOnClickListener { selectPdf()}

    }

    private fun selectPdf() {
        val pdfIntent = Intent(Intent.ACTION_GET_CONTENT)
        pdfIntent.type = "application/pdf"
        pdfIntent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(pdfIntent, 12)
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            12 -> if (resultCode == RESULT_OK) {
                pdfUri = data?.data!!
                val uri: Uri = data.data!!
                val uriString: String = uri.toString()
                var pdfName: String? = null
                if (uriString.startsWith("content://")) {
                    var myCursor: Cursor? = null
                    try {
                        // Getting Pdf name
                        myCursor = requireActivity().contentResolver.query(uri, null, null, null, null)
                        if (myCursor != null && myCursor.moveToFirst()) {
                            pdfName = myCursor.getString(myCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                            // Convert PDF to file
                            val destinationFile = createPdfFile()
                            if (destinationFile != null) {
                                convertPdfToFile(destinationFile, requireContext(), pdfUri)
                                val filePath = destinationFile.path
                                sharedViewModel.saveUploadedDocument( PdfFileModel(fileName = pdfName, filePath = filePath))
                                fileDetails= sharedViewModel.getDocumentList()
                                adapter.notifyDataSetChanged()
                            }
                            else{
                                Toast.makeText(requireActivity(), "Something went wrong. Try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } finally {
                        myCursor?.close()
                    }
                }
            }
        }


    }

    private fun convertPdfToFile(destinationFile: File, context: Context, pdfUri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            context.contentResolver.openInputStream(pdfUri)?.use { inputStream ->
                FileOutputStream(destinationFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        }
    }

    private fun createPdfFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return try {
            File.createTempFile(
                "PDF_${timeStamp}_",
                ".pdf",
                storageDir
            )
        } catch (e: Exception) {
            Log.e("InvestorSetup3Fragment", "Failed to create PDF file: ${e.message}", e)
            null
        }
    }

    override fun onDeleteDocument(position: Int) {
        sharedViewModel.deleteDocumentFromList(position)
        fileDetails= sharedViewModel.getDocumentList()
        adapter.notifyDataSetChanged()
    }


}