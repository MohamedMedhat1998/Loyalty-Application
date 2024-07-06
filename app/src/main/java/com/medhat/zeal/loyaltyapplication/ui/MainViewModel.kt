package com.medhat.zeal.loyaltyapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.medhat.zeal.loyaltyapplication.data.repo.DiscountRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val discountRepo: DiscountRepo
) : ViewModel() {
    private val _discount = MutableStateFlow<Long?>(null)
    val discount = _discount.asStateFlow()

    init {
        viewModelScope.launch {
            discountRepo.getDiscount()
                .collect {
                    _discount.value = it
                }
        }
    }

    fun onSaveClicked(discountValue: Long) {
        viewModelScope.launch {
            discountRepo.saveDiscount(discountValue)
        }
    }
}