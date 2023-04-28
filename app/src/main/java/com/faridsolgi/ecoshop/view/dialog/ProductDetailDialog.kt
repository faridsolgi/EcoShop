package com.faridsolgi.ecoshop.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.view.ViewGroup
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.DialogProductDetailBinding
import com.faridsolgi.ecoshop.model.ProductResponse
import com.squareup.picasso.Picasso


class ProductDetailDialog :
    BaseDialog<DialogProductDetailBinding>(DialogProductDetailBinding::inflate) {

    companion object {
        const val REQ_KEY = "ProductDetailDialogReqKey"
        const val PRODUCT_KEY = "PRODUCT_KEY"
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        val back = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(back, 32)
        dialog!!.window!!.setBackgroundDrawable(inset)

    }

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
                    .into(binding.imgProductPic)
                    binding.apply {
                        tvTitle.text = product?.title
                        tvDesc.text = product?.description
                        tvPrice.text =getString(R.string.dollorSignWithPrice, product?.price.toString())
                        tvRating.text = product?.rating?.rate.toString()
                        ctaAddToCart.setOnClickListener {

                        }
                    }


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}