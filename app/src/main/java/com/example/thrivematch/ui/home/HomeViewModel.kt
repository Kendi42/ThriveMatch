package com.example.thrivematch.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository): BaseViewModel(repository) {
    private var likedCardsList: MutableList<CardSwipeItemModel> = mutableListOf()
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

    fun saveLikedCard(savedCard: CardSwipeItemModel){
        Log.i("Saved Liked Card (VM)", savedCard.name)
        likedCardsList.add(savedCard)
        Log.i("Liked from Viewmodel", likedCardsList.toString())
    }

    fun getLikedCards(): MutableList<PendingMatchModel>{
        return likedCardsList.map { cardSwipeItem ->
            convertToPendingMatch(cardSwipeItem)
        }.toMutableList()
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

    private fun convertToPendingMatch(cardSwipeItem: CardSwipeItemModel): PendingMatchModel {
        return PendingMatchModel(
            imageURL = cardSwipeItem.imageURL,
            name = cardSwipeItem.name
        )
    }


}