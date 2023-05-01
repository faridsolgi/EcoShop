package com.faridsolgi.ecoshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faridsolgi.ecoshop.model.ProductResponse
import com.faridsolgi.ecoshop.model.ProductSortEnum
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import com.faridsolgi.ecoshop.repository.HomeRepository
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class HomeViewModel @Inject constructor(val repository: HomeRepository) :ViewModel() {

    private val _productCategories : MutableLiveData<List<String>> =MutableLiveData()
     val productCategories : LiveData<List<String>> =_productCategories
    private val _productList: MutableLiveData<List<ProductResponse>> =MutableLiveData()
    val productList : LiveData<List<ProductResponse>> =_productList
    private val _productResult: MutableLiveData<ProductResponse> =MutableLiveData()
    val productResult : LiveData<ProductResponse> =_productResult
    private val _notifyMsg : MutableLiveData<String> =MutableLiveData()
     val notifyMsg : LiveData<String> =_notifyMsg

    suspend fun getProductCategories() {
            repository.getProductCategories().collectLatest {
                if (it.isSuccessful){
                    _productCategories.postValue(it.body())
                }else{
                    _notifyMsg.postValue(it.errorBody()?.string())
                }
            }
    }

    suspend fun getProductByCategoryName(category: String) {
        repository.getProductByCategoryName(category).collectLatest {
            if (it.isSuccessful){
                _productList.postValue(it.body())
            }else{
                _notifyMsg.postValue(it.errorBody()?.string())
            }
        }
    }

    suspend fun getProductDetailById(id:Int?){
        repository.getProductDetailById(id).collectLatest {
            if (it.isSuccessful){
                _productResult.postValue(it.body())
            }else{
                _notifyMsg.postValue(it.errorBody()?.string())
            }
        }
    }
    suspend fun getProductWithSort(productSortEnum: ProductSortEnum){
        repository.getProductWithSort(productSortEnum).collectLatest {
            if (it.isSuccessful){
                _productList.postValue(it.body())
            }else{
                _notifyMsg.postValue(it.errorBody()?.string())
            }
        }
    }
    suspend fun getAllProduct(){
        repository.getAllProduct().collectLatest {
            if (it.isSuccessful){
                _productList.postValue(it.body())
            }else{
                _notifyMsg.postValue(it.errorBody()?.string())
            }
        }
    }
    suspend fun addToCart(shoppingCart: ShoppingCart){
        repository.addToCert(shoppingCart)
    }


}