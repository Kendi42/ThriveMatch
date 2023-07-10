package com.example.thrivematch.ui.more_info

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.models.PdfFileModel
import com.example.thrivematch.data.repository.DocumentSharingRepository
import com.example.thrivematch.data.repository.HomeRepository
import kotlinx.coroutines.launch
import java.util.LinkedList

class DocumentSharingSharedViewModel : ViewModel(){
    private val repository: DocumentSharingRepository = DocumentSharingRepository()

    fun saveUploadedDocument(uploadedDocument: PdfFileModel) = viewModelScope.launch {
        repository.saveUploadedDocument(uploadedDocument)
    }

    fun getDocumentList():LinkedList<PdfFileModel>{
        return repository.getDocumentList()
    }
    fun deleteDocumentFromList(documentForDeletion: PdfFileModel)= viewModelScope.launch {
        repository.deleteDocumentFromList(documentForDeletion)
    }

}