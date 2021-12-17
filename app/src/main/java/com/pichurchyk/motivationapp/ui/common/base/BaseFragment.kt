package com.pichurchyk.motivationapp.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.pichurchyk.motivationapp.ui.common.hideKeyboard
import com.pichurchyk.motivationapp.ui.common.utils.snackBar

abstract class BaseFragment<VM : ViewModel, DB : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
    viewModelClass: Class<VM>
) : Fragment() {

    open fun init() {}

    lateinit var binding: DB
    val viewModel: VM by lazy { ViewModelProvider(this).get(viewModelClass) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    fun navigate(actionId: Int) {
        view?.let { currentView ->
            Navigation.findNavController(currentView).navigate(actionId)
        }
    }

    fun clearFocus() {
        binding.root.clearFocus()
        requireActivity().hideKeyboard(binding.root)
    }

    fun snackBar(view: View, text: String) {
        requireContext().snackBar(view, text)
    }
}