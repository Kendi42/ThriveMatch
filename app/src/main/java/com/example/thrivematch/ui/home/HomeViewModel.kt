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
import com.example.thrivematch.data.models.MatchedModel
import com.example.thrivematch.data.models.PendingMatchModel
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.data.response.LoginResponse
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
    var matchedCardsList: MutableLiveData<MutableList<MatchedModel>?> = MutableLiveData()


    init {
        viewModelScope.launch {
            likedCardsList.value = getLikedCards()
            matchedCardsList.value = getMatchedCards()

        }
    }
    private val gson = Gson()
    private val _cardItems: MutableLiveData<MutableList<CardSwipeItemModel>> = MutableLiveData()
    val unseenCardItems: LiveData<MutableList<CardSwipeItemModel>> = getAllCards()

    val cardItems: LiveData<MutableList<CardSwipeItemModel>>
        get()= _cardItems
    private var selectedCard: CardSwipeItemModel? = null
    private val _cardLoadingResponse : MutableLiveData<Resource<List<CardSwipeItemModel>>> = MutableLiveData()
    val cardLoadingResponse: LiveData<Resource<List<CardSwipeItemModel>>>
        get()= _cardLoadingResponse


    private fun getAllCards(): LiveData<MutableList<CardSwipeItemModel>>{
        viewModelScope.launch {
            repository.getCardData().collect{resource->
                _cardLoadingResponse.value = resource
                Log.i("Whats in resource", resource.toString())
                when (resource) {
                    is Resource.Success->{
                        _cardItems.value = resource.value.toMutableList()
                        Log.i("Whats in _cardItems", _cardItems.value.toString())
                    }
                    is Resource.Loading ->{
                    }
                    is Resource.Failure->{
                    }
                }
                }
        }
        return _cardItems
    }

    fun alterUnseenCards(){
        Log.i("Unseen Cards Altered", "Position to be removed: 0")
        // Todo: Instead of just removing the card from here, we need to make a call to the backend
        unseenCardItems.value?.removeAt(0)
        Log.i("Unseen Cards Altered", "New unseen Cards ${unseenCardItems.value}")
    }

    // Liked Fragment Functions
    fun saveLikedCard(savedCard: CardSwipeItemModel) = viewModelScope.launch{
        Log.i("In liked viewmodel", "In liked viewmodel")
        repository.saveLikedCard(savedCard)
        Log.i("Liked Cards List Before", likedCardsList.value.toString())
        viewModelScope.launch {
            Log.i("In coroutine scope", "scope")
            likedCardsList.value = getLikedCards()
        }
        Log.i("Liked Cards List After", likedCardsList.value.toString())

    }
    suspend fun getLikedCards(): MutableList<PendingMatchModel>{
        Log.i("Inside getlikedvm", "Inside get liked cards vm")
        return repository.getLikedCards()
    }


    // Matched Fragment Functions
    suspend fun getMatchedCards(): MutableList<MatchedModel> {
        return repository.getMatchedCards()
    }

    // Selected Cards From Home Fragment and Liked Fragment
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