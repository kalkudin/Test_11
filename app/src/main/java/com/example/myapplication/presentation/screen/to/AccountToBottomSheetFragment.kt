package com.example.myapplication.presentation.screen.to

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.myapplication.databinding.FragmentAccountToBottomSheetLayoutBinding
import com.example.myapplication.presentation.event.ToEvent
import com.example.myapplication.presentation.model.BankAccountView
import com.example.myapplication.presentation.state.ToState
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountToBottomSheetFragment : BottomSheetDialogFragment() {

    interface OnAccountDetailsFetchedListener {
        fun onAccountDetailsFetched(account: BankAccountView)
    }

    var accountDetailsFetchedListener: OnAccountDetailsFetchedListener? = null

    private var _binding: FragmentAccountToBottomSheetLayoutBinding? = null
    private val binding get() = _binding!!

    private val toViewModel : AccountToViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAccountToBottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUserAccount()
        bindToFlow()
    }

    private fun bindUserAccount() {
        with(binding) {
            btnSubmit.setOnClickListener {
                toViewModel.onEvent(ToEvent.GetUserAccount(cardNumber = "1234 5678 9012 3456 7890 123", id = "ABCD1234567", number = "123-456-789"))

            }
        }
    }

    private fun bindToFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                toViewModel.toFlow.collect { state ->
                    handleState(state)
                }
            }
        }
    }

    private fun handleState(state : ToState) {
        state.errorMessage?.let { message ->
            handleErrorMessage(errorMessage = message)
            toViewModel.onEvent(ToEvent.ResetState)
        }

        state.accountList?.let { account ->
            accountDetailsFetchedListener?.onAccountDetailsFetched(account)
            handleSuccess()
            dismiss()
        }
    }

    private fun handleErrorMessage(errorMessage : String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    private fun handleSuccess() {
        Snackbar.make(binding.root, "Success!", Snackbar.LENGTH_LONG).setAction("OK"){}.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}