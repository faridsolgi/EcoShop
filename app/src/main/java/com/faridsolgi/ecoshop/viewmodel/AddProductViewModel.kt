package com.faridsolgi.ecoshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faridsolgi.ecoshop.model.ProductResponse
import com.faridsolgi.ecoshop.repository.AddProductRepository
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class AddProductViewModel @Inject constructor(private val repository: AddProductRepository) : ViewModel() {

    private val _addNewProduct : MutableLiveData<ProductResponse> = MutableLiveData()
    val addNewProduct : LiveData<ProductResponse> =_addNewProduct
    private val _notifyMsg : MutableLiveData<String> =MutableLiveData()
    val notifyMsg : LiveData<String> =_notifyMsg
    suspend fun addNewProduct(productResponse: ProductResponse){
        repository.addNewProduct(productResponse).collectLatest {
            if(it.isSuccessful){
                _addNewProduct.postValue(it.body())
            }else{
                _notifyMsg.postValue(it.errorBody()?.string())
            }
        }
    }

}