package com.example.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemBankAccountsLayoutBinding
import com.example.myapplication.presentation.model.BankAccountView

class BankAccountsRecyclerAdapter(private val onItemClick: (BankAccountView) -> Unit): ListAdapter<BankAccountView, BankAccountsRecyclerAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBankAccountsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account)
    }

    inner class ViewHolder(private val binding: ItemBankAccountsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(account: BankAccountView) {
            with(binding) {
                tvCardName.text = account.accountName
                tvNumber.text = account.accountNumber
                tvType.text = account.currency
                tvCardType.text = account.cardType

                root.setOnClickListener { onItemClick(account) }
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<BankAccountView>() {
        override fun areItemsTheSame(oldItem: BankAccountView, newItem: BankAccountView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BankAccountView, newItem: BankAccountView): Boolean {
            return oldItem == newItem
        }
    }
}