package com.example.thrivematch.ui.home

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.ui.base.BaseViewModel
import com.example.thrivematch.util.CommonSharedPreferences
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository): BaseViewModel(repository) {
    var likedCardsList: MutableLiveData<MutableList<PendingMatchModel>?> = MutableLiveData()
    init {
        viewModelScope.launch {
            likedCardsList.value = getLikedCards()
        }
    }
    private val _cardItems: MutableLiveData<MutableList<CardSwipeItemModel>> = MutableLiveData()
    val unseenCardItems: LiveData<MutableList<CardSwipeItemModel>> = getAllCards()
    val cardItems: LiveData<MutableList<CardSwipeItemModel>>
        get()= _cardItems
    private var selectedCard: CardSwipeItemModel? = null

    private fun getAllCards(): LiveData<MutableList<CardSwipeItemModel>>{
        viewModelScope.launch {
            _cardItems.value = repository.getBusinessCardData()
        }
        return _cardItems
    }

    fun saveLikedCard(savedCard: CardSwipeItemModel) = viewModelScope.launch{
        repository.saveLikedCard(savedCard)
    }

    suspend fun getLikedCards(): MutableList<PendingMatchModel>{
        Log.i("Inside getlikedvm", "Inside get liked cards vm")
        return repository.getLikedCards()
    }

    fun alterUnseenCards(){
        Log.i("Unseen Cards Altered", "Position to be removed: 0")
        unseenCardItems.value?.removeAt(0)
        Log.i("Unseen Cards Altered", "New unseen Cards ${unseenCardItems.value}")

    }

    fun setSelectedCard(it: CardSwipeItemModel) {
        selectedCard = it
    }
    fun getSelectedCard(): CardSwipeItemModel? {
        return selectedCard
    }


}