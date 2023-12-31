package com.example.thrivematch.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.thrivematch.data.network.RemoteDataSource
import com.example.thrivematch.data.network.UserApi
import com.example.thrivematch.data.repository.BaseRepository
import com.example.thrivematch.util.CommonSharedPreferences
import com.example.thrivematch.util.Constants
import kotlinx.coroutines.launch

abstract class BaseFragment<VM: BaseViewModel, B: ViewBinding, R: BaseRepository> :Fragment() {

    protected lateinit var binding: B
    protected val remoteDataSource= RemoteDataSource() // to create API instance in fragments
    protected lateinit var viewModel: VM
    private lateinit var commonSharedPreferences: CommonSharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel= ViewModelProvider(this, factory)[getViewModel()]
        commonSharedPreferences = CommonSharedPreferences(requireContext())

        return binding.root

    }

    fun logout()= lifecycleScope.launch {
        val authToken = commonSharedPreferences.getStringData(Constants.AUTHTOKEN)
        val api = remoteDataSource.buildApi(UserApi::class.java, authToken)
        viewModel.logout(api)
        commonSharedPreferences.clearSharedPreferences()
        //Todo: Navigate to login page
        Toast.makeText(requireActivity(), "Logout", Toast.LENGTH_SHORT).show()
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?):B

    abstract fun getFragmentRepository(): R


}