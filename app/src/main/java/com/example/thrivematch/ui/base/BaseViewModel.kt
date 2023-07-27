package com.example.thrivematch.ui.base

import androidx.lifecycle.ViewModel
import com.example.thrivematch.data.repository.BaseRepository

abstract class BaseViewModel (private val repository: BaseRepository): ViewModel() {

}