package com.example.thrivematch.data.repository

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.thrivematch.data.models.PdfFileModel
import kotlinx.coroutines.launch
import java.text.ParsePosition
import java.util.*

class DocumentSharingRepository: BaseRepository() {
    var pdfDocumentList = LinkedList<PdfFileModel>()

    suspend fun saveUploadedDocument(uploadedDocument: PdfFileModel) {
        Log.i("Uploaded PDF file", uploadedDocument.toString())
        pdfDocumentList.addFirst(uploadedDocument)
        Log.i("All PDF files", pdfDocumentList.toString())
    }

     fun getDocumentList(): LinkedList<PdfFileModel> {
        return pdfDocumentList
    }

    suspend fun deleteDocumentFromList(position: Int){
        pdfDocumentList.removeAt(position)
        Log.i("PDFs after Deletion", pdfDocumentList.toString())

    }

}