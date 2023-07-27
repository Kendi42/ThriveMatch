package com.example.thrivematch.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.thrivematch.data.network.RemoteDataSource
import com.example.thrivematch.data.repository.BaseRepository
import com.example.thrivematch.util.CommonSharedPreferences


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
        commonSharedPreferences = CommonSharedPreferences(requireContext())
        val factory = ViewModelFactory(getFragmentRepository(), commonSharedPreferences)
        viewModel= ViewModelProvider(this, factory)[getViewModel()]

        return binding.root
    }


    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?):B

    abstract fun getFragmentRepository(): R


}