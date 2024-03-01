package com.example.myapplication.presentation.screen.from

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountFromBottomSheetLayoutBinding
import com.example.myapplication.presentation.adapter.BankAccountsRecyclerAdapter
import com.example.myapplication.presentation.event.FromEvent
import com.example.myapplication.presentation.model.BankAccountView
import com.example.myapplication.presentation.state.FromState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFromBottomSheetFragment : BottomSheetDialogFragment() {

    interface OnAccountSelectedListener {
        fun onAccountSelected(bankAccount: BankAccountView)
    }

    var accountSelectedListener: OnAccountSelectedListener? = null

    private var _binding: FragmentAccountFromBottomSheetLayoutBinding? = null
    private val binding get() = _binding!!

    private val fromViewModel : AccountFromViewModel by viewModels()

    private lateinit var adapter: BankAccountsRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAccountFromBottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindRecyclerView()
        bindUserAccounts()
        bindFromFlow()
    }

    private fun bindUserAccounts() {
        fromViewModel.onEvent(FromEvent.GetUserAccounts)
    }

    private fun bindRecyclerView() {
        adapter = BankAccountsRecyclerAdapter { bankAccount ->
            handleClick(bankAccount)
        }
        binding.accountRv.adapter = adapter
    }
    private fun bindFromFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                fromViewModel.fromState.collect { state ->
                    handleState(state)
                }
            }
        }
    }

    private fun handleState(state : FromState) {
        state.errorMessage?.let {
            errorMessage -> handleErrorMessage(errorMessage)
        }

        state.accountList?.let { accounts ->
            adapter.submitList(accounts)
        }
    }

    private fun handleClick(bankAccount: BankAccountView) {
        accountSelectedListener?.onAccountSelected(bankAccount)
        dismiss()
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}