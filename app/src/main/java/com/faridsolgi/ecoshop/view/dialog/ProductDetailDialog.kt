package com.faridsolgi.ecoshop.view.dialog

import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.DialogProductDetailBinding
import com.faridsolgi.ecoshop.model.ProductResponse
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import com.faridsolgi.ecoshop.viewmodel.ProductDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailDialog :
    BaseDialog<DialogProductDetailBinding>(DialogProductDetailBinding::inflate) {

    companion object {
        const val REQ_KEY = "ProductDetailDialogReqKey"
        const val PRODUCT_KEY = "PRODUCT_KEY"
    }

@Inject
lateinit var viewModel : ProductDetailViewModel

    override fun dialogBody() {

        parentFragmentManager.setFragmentResultListener(
            REQ_KEY, viewLifecycleOwner
        ) { requestKey, result ->
            try {


                var product: ProductResponse?

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    product = result.getSerializable(PRODUCT_KEY, ProductResponse::class.java)
                } else {
                    product = result.get(PRODUCT_KEY) as ProductResponse
                }
                Picasso.get()
                    .load(product?.image)
                    .placeholder(ContextCompat.getDrawable(requireContext(),R.drawable.img_placeholder)!!)
                    .into(binding.imgProductPic)
                    binding.apply {
                        tvTitle.text = product?.title
                        tvDesc.text = product?.description
                        tvPrice.text =getString(R.string.dollorSignWithPrice, product?.price.toString())
                        tvRating.text = product?.rating?.rate.toString()
                        ctaAddToCart.setOnClickListener {

                        }
                    }

                binding.ctaAddToCart.setOnClickListener {
                    lifecycleScope.launch {
                        viewModel.addToCart(
                            ShoppingCart(
                                productId = product?.id ?:0,
                                name = product?.title,
                                price = product?.price,
                                imageUrl = product?.image
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }
}