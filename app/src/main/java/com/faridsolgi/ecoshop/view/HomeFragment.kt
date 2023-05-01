package com.faridsolgi.ecoshop.view

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.faridsolgi.ecoshop.adapter.ProductCategoryAdapter
import com.faridsolgi.ecoshop.adapter.ProductListAdapter
import com.faridsolgi.ecoshop.databinding.FragmentHomeBinding
import com.faridsolgi.ecoshop.model.ProductResponse
import com.faridsolgi.ecoshop.model.ProductSortEnum
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import com.faridsolgi.ecoshop.model.utils.alertUser
import com.faridsolgi.ecoshop.model.utils.initRecycler
import com.faridsolgi.ecoshop.model.utils.setDrawableTint
import com.faridsolgi.ecoshop.view.dialog.ProductDetailDialog
import com.faridsolgi.ecoshop.viewmodel.HomeViewModel
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    ProductCategoryAdapter.SetOnCategoriesItemClick,
    ProductListAdapter.SetOnProductClickListener, ProductListAdapter.SetOnAddToCartClickListener {
    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var productCategoryAdapter: ProductCategoryAdapter

    @Inject
    lateinit var productListAdapter: ProductListAdapter
    override fun fragmentBody() {
        initRecyclerviews()
        productSorting()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAllProduct()
        }





        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getProductCategories()
        }
        viewModel.apply {
            productCategories.observe(viewLifecycleOwner) {
                productCategoryAdapter.categoriesList = it
            }
            notifyMsg.observe(viewLifecycleOwner) {
                alertUser(view = binding.root, it)
            }
            productList.observe(viewLifecycleOwner) {
                productListAdapter.productList = it
            }

            productResult.observe(viewLifecycleOwner) {
                val bundle = Bundle()
                bundle.putSerializable(ProductDetailDialog.PRODUCT_KEY, it)
                parentFragmentManager.setFragmentResult(ProductDetailDialog.REQ_KEY, bundle)
                ProductDetailDialog().show(
                    parentFragmentManager,
                    ProductDetailDialog::class.simpleName
                )
            }
        }

    }

    private fun productSorting() {
        val colorPrimary =
            MaterialColors.getColor(binding.root, androidx.appcompat.R.attr.colorPrimary)
        val colorOnBackground = MaterialColors.getColor(
            binding.root,
            com.google.android.material.R.attr.colorOnBackground
        )
        binding.btnAsc.setOnClickListener {
            binding.btnAsc.setTextColor(colorPrimary)
           binding.btnAsc.setDrawableTint(colorPrimary)
           binding.btnDesc.setDrawableTint(colorOnBackground)
            binding.btnDesc.setTextColor(colorOnBackground)

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getProductWithSort(ProductSortEnum.asc)
            }
        }
        binding.btnDesc.setOnClickListener {
            binding.btnAsc.setTextColor(colorOnBackground)
            binding.btnDesc.setTextColor(colorPrimary)
            binding.btnAsc.setDrawableTint(colorOnBackground)
            binding.btnDesc.setDrawableTint(colorPrimary)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getProductWithSort(ProductSortEnum.desc)
            }
        }

    }

    private fun initRecyclerviews() {
        productCategoryAdapter._setOnCategoriesItemClick = this
        productListAdapter._setOnProductClickListener = this
        productListAdapter._setOnAddToCartClickListener = this
        binding.rvProductCategory.initRecycler(
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false),
            productCategoryAdapter
        )
        binding.rvProducts.initRecycler(
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false),
            productListAdapter
        )
    }

    override fun onProductCategoryClickListener(category: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getProductByCategoryName(category)
        }
    }

    override fun onProductClick(productResponse: ProductResponse) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getProductDetailById(productResponse.id)
        }
    }

    override fun onAddToCartClick(productResponse: ProductResponse) {
        lifecycleScope.launch {
            viewModel.addToCart(ShoppingCart(
                productId = productResponse.id ?:0,
                name = productResponse.title,
                price = productResponse.price,
                imageUrl = productResponse.image
            ))
        }

    }
}