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
import okhttp3.internal.notifyAll

class HomeViewModel(private val repository: HomeRepository,
                    private val sharedPreferences: CommonSharedPreferences
                    ): BaseViewModel(repository) {
    private var _likedCardsList: MutableLiveData<MutableList<PendingMatchModel>?> = MutableLiveData()
    val likedCardsList: LiveData<MutableList<PendingMatchModel>?>
        get()= _likedCardsList

    var matchedCardsList: MutableLiveData<MutableList<MatchedModel>?> = MutableLiveData()
    init {
        viewModelScope.launch {
           // _likedCardsList.value = getLikedCards()
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
                when (resource) {
                    is Resource.Success->{
                        _cardItems.value = resource.value.toMutableList()
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
        unseenCardItems.value?.removeAt(0)
    }

    // Liked Fragment Functions
    fun saveLikedCard(savedCard: CardSwipeItemModel) = viewModelScope.launch{
        Log.i("liked list before call", likedCardsList.value.toString())
        repository.saveLikedCard(savedCard)
        Log.i("Whats in liked list", likedCardsList.value.toString())
    }
    suspend fun getLikedCards(): MutableList<PendingMatchModel>{
       // Log.i("Whats in get liked", repository.getLikedCards().toString())
        _likedCardsList.value = repository.getLikedCards()
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