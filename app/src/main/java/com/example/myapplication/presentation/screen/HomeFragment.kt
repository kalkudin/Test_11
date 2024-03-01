package com.example.myapplication.presentation.screen

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.databinding.FragmentHomeLayoutBinding
import com.example.myapplication.presentation.base.BaseFragment
import com.example.myapplication.presentation.event.HomeEvent
import com.example.myapplication.presentation.model.BankAccountView
import com.example.myapplication.presentation.screen.from.AccountFromBottomSheetFragment
import com.example.myapplication.presentation.screen.to.AccountToBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeLayoutBinding>(FragmentHomeLayoutBinding::inflate),
    AccountFromBottomSheetFragment.OnAccountSelectedListener,
    AccountToBottomSheetFragment.OnAccountDetailsFetchedListener {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun bind() {
        bindCurrency()
    }

    override fun bindViewActionListeners() {
        bindToBottomSheet()
        bindFromBottomSheet()
    }

    override fun bindObservers() {
        bindCurrencyFlow()
    }

    private fun bindCurrency() {
        homeViewModel.onEvent(HomeEvent.GetCurrency)
    }

    private fun bindFromBottomSheet() {
        binding.btnFromAccount.setOnClickListener {
            val bottomSheetFragment = AccountFromBottomSheetFragment().apply {
                accountSelectedListener = this@HomeFragment
            }
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun bindToBottomSheet() {
        binding.btnToAccount.setOnClickListener {
            val bottomSheetFragment = AccountToBottomSheetFragment().apply {
                accountDetailsFetchedListener = this@HomeFragment
            }
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun bindCurrencyFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.currencyFlow.collect {

                }
            }
        }
    }

    override fun onAccountSelected(bankAccount: BankAccountView) {
        Log.d("HomeFragment", "Selected Account: ${bankAccount.accountName}, Number: ${bankAccount.accountNumber}")
        handleAccountSelected(account = bankAccount)
    }

    override fun onAccountDetailsFetched(account: BankAccountView) {
        Log.d("HomeFragment", "Account Details Fetched: ${account.accountName}")
        handleRecipientAccountSelected(account = account)
    }

    private fun handleAccountSelected(account: BankAccountView) {
        with(binding) {
            fromContainer.visibility = View.VISIBLE
            tvCardName.text = account.accountName
            tvNumber.text = account.accountNumber
            tvType.text = account.currency
            tvCardType.text = account.cardType
        }
    }

    private fun handleRecipientAccountSelected(account: BankAccountView) {
        with(binding) {
            toContainer.visibility = View.VISIBLE
            tvCardNameTo.text = account.accountName
            tvNumberTo.text = account.accountNumber
            tvTypeTo.text = account.currency
            tvCardTypeTo.text = account.cardType
        }
    }
}