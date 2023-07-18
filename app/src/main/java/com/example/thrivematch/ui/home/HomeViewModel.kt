package com.example.thrivematch.ui.home

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.util.pool.FactoryPools.Resetter
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.ui.base.BaseViewModel
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import com.google.gson.Gson
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository,
                    private val sharedPreferences: CommonSharedPreferences
                    ): BaseViewModel(repository) {
    var likedCardsList: MutableLiveData<MutableList<PendingMatchModel>?> = MutableLiveData()
    init {
        viewModelScope.launch {
            likedCardsList.value = getLikedCards()
//
//            repository.getBusinessCardData().collect{resource ->
//                when(resource){
//                    is Resource.Loading->{
//                        Log.i("Handle Loading,", "Handle")
//                    }
//                    is Resource.Success ->{
//                        _cardItems.value= resource.value!!
//                    }
//                    is Resource.Failure->{
//                    }
//                }
//            }
        }
    }
    private val gson = Gson()
    private val _cardItems: MutableLiveData<MutableList<CardSwipeItemModel>> = MutableLiveData()
//    val unseenCardItems: LiveData<MutableList<CardSwipeItemModel>> = _cardItems
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
        // Todo: Instead of just removing the card from here, we need to make a call to the backend
        unseenCardItems.value?.removeAt(0)
        Log.i("Unseen Cards Altered", "New unseen Cards ${unseenCardItems.value}")
    }

    fun setSelectedCard(it: CardSwipeItemModel) {
        selectedCard = it
        sharedPreferences.saveStringData(Constants.SELECTEDCARD, gson.toJson(selectedCard))
    }

    fun getSelectedCard(): CardSwipeItemModel? {
        val stringData= sharedPreferences.getStringData(Constants.SELECTEDCARD)
        selectedCard=convertStringToCardSwipeItemModel(stringData)
        return selectedCard
    }

    private fun convertStringToCardSwipeItemModel(stringData: String): CardSwipeItemModel? {
        return gson.fromJson(stringData, CardSwipeItemModel::class.java)
    }
}