package com.example.rickandmortyapi.data.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.rickandmortyapi.utils.Resource

abstract class BaseFragment<VB: ViewBinding, VM: ViewModel>: Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected fun <T> observeData(liveData: LiveData<Resource<T>>, onSuccess: (T) -> Unit) {
        liveData.observe(viewLifecycleOwner) { resource ->
            when(resource) {
                is Resource.Loading -> showLoading()
                is Resource.Success -> {
                    hideLoading()
                    resource.data?.let { onSuccess(it) }
                }
                is Resource.Error -> {
                    hideLoading()
                    showError(resource.message)
                }
            }
        }
    }

    protected open fun showLoading() {
        // Override to show loading state
    }

    protected open fun hideLoading() {
        // Override to hide loading state
    }

    protected open fun showError(message: String?) {
        Toast.makeText(requireContext(), message ?: "Error occurred" , Toast.LENGTH_SHORT).show()
    }
}