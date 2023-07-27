package com.example.thrivematch.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.thrivematch.R
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.network.HomeDataAPI
import com.example.thrivematch.data.network.Resource
import com.example.thrivematch.data.repository.HomeRepository
import com.example.thrivematch.data.roomdb.database.AppDatabase
import com.example.thrivematch.databinding.FragmentHomeBinding
import com.example.thrivematch.ui.HomeActivity
import com.example.thrivematch.ui.account_setup.SharedAccountSetupViewModel
import com.example.thrivematch.ui.adapters.CardSwipeAdapter
import com.example.thrivematch.ui.base.BaseFragment
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import com.example.thrivematch.util.handleApiError
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.Duration
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting

class HomeFragment:BaseFragment<HomeViewModel, FragmentHomeBinding,HomeRepository>(){
    private lateinit var cardStackLayoutManager: CardStackLayoutManager
    private lateinit var adapter: CardSwipeAdapter
    private var cardItems: List<CardSwipeItemModel> = emptyList()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentHomeBinding.bind(view)
        binding.homeProgressBar.isVisible= false
        viewModel.cardLoadingResponse.observe(viewLifecycleOwner, Observer {
            binding.homeProgressBar.isVisible= false
            when(it){
                is Resource.Success ->{
                    binding.homeProgressBar.isVisible= false
                }
                is Resource.Failure -> handleApiError(it){
                    Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    binding.homeProgressBar.isVisible= false
                }

                is Resource.Loading->{
                    binding.homeProgressBar.isVisible= true

                }
            }
        })

        initCardStackLayoutManager()
        binding.cardStackView.layoutManager= cardStackLayoutManager
        val cardStackView = binding.cardStackView
        adapter = CardSwipeAdapter(emptyList()) { newcardSwipeItem ->
            setOnCardItemClicked(newcardSwipeItem)
        }

        binding.cardStackView.adapter = adapter
        updateCardView()

        binding.ivLeftSwipeIndicator.setOnClickListener {
            if(cardStackLayoutManager.topPosition < cardItems.size){
                binding.ivLeftSwipeIndicator.setBackgroundResource(R.drawable.selected_left_swipe_indicator_shape)
            }
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            cardStackLayoutManager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        binding.ivRightSwipeIndicator.setOnClickListener {
            if(cardStackLayoutManager.topPosition < cardItems.size){
                binding.ivRightSwipeIndicator.setBackgroundResource(R.drawable.selected_right_swipe_indicator_shape)
            }
            cardStackLayoutManager.setDirections(Direction.HORIZONTAL)
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Slow.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            cardStackLayoutManager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

    }

    private fun initCardStackLayoutManager() {
        cardStackLayoutManager= CardStackLayoutManager(requireContext(), object: CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {
                if(direction == Direction.Left && ratio>=0.5){
                    binding.ivLeftSwipeIndicator.setBackgroundResource(R.drawable.selected_left_swipe_indicator_shape)
                    binding.ivRightSwipeIndicator.setBackgroundResource(R.drawable.right_swipe_indicator_shape)
                }
                else if(direction == Direction.Right  && ratio>=0.5){
                    binding.ivRightSwipeIndicator.setBackgroundResource(R.drawable.selected_right_swipe_indicator_shape)
                    binding.ivLeftSwipeIndicator.setBackgroundResource(R.drawable.left_swipe_indicator_shape)
                }
            }

            override fun onCardSwiped(direction: Direction?) {
                if(direction == Direction.Left){
                    binding.ivLeftSwipeIndicator.setBackgroundResource(R.drawable.left_swipe_indicator_shape)
                }
                // Saving liked cards
                else if(direction == Direction.Right){
                    viewModel.saveLikedCard(cardItems[0])

                    binding.ivRightSwipeIndicator.setBackgroundResource(R.drawable.right_swipe_indicator_shape)
                }
                viewModel.alterUnseenCards()
                updateCardView()
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
                binding.ivRightSwipeIndicator.setBackgroundResource(R.drawable.right_swipe_indicator_shape)
                binding.ivLeftSwipeIndicator.setBackgroundResource(R.drawable.left_swipe_indicator_shape)
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
            }

        } )

    }

    private fun updateCardView() {
        // Business Cards
        viewModel.unseenCardItems.observe(viewLifecycleOwner, Observer{ newCardItems ->
            cardItems = newCardItems
            adapter.setCardSwipeItems(newCardItems)
        })
    }

    private fun setOnCardItemClicked(it: CardSwipeItemModel) {
        viewModel.setSelectedCard(it)
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_homeFragment_to_moreInfoFragment)
        }, 350L)

    }

    override fun getViewModel()= HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )=FragmentHomeBinding.inflate(inflater, container, false)

    override fun getFragmentRepository()= HomeRepository(remoteDataSource.buildApi(HomeDataAPI::class.java), AppDatabase.invoke(requireContext()),CommonSharedPreferences(requireActivity()))
}