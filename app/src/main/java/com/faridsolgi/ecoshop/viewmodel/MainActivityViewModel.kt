package com.faridsolgi.ecoshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import com.faridsolgi.ecoshop.repository.MainActivityRepository
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val repository: MainActivityRepository) :ViewModel() {
     val cartListLiveData :LiveData<List<ShoppingCart>>
     = repository.getCartList().asLiveData()
     val totalCartItemLiveData :LiveData<Int>
     = repository.getTotalCartItem().asLiveData()







}