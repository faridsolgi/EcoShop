package com.faridsolgi.ecoshop.view.dialog

import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.DialogAddProductResultBinding
import com.faridsolgi.ecoshop.model.ProductResponse
import com.squareup.picasso.Picasso

class AddProductResultDialog: BaseDialog<DialogAddProductResultBinding>(DialogAddProductResultBinding::inflate) {
    companion object {
        const val REQ_KEY = "AddProductResultDialog"
        const val PRODUCT_KEY = "AddProductResultDialog_PRODUCT_KEY"
    }
    override fun dialogBody() {
            parentFragmentManager.setFragmentResultListener(REQ_KEY,viewLifecycleOwner){ requestKey, result ->
                try {
                    var product: ProductResponse?

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        product = result.getSerializable(PRODUCT_KEY, ProductResponse::class.java)
                    } else {
                        product = result.get(PRODUCT_KEY) as ProductResponse
                    }

                    binding.tvTitle.text = product?.title
                    binding.tvCategory.text = getString(R.string.category_,product?.category)
                    binding.tvDesc.text = product?.description
                    binding.tvPrice.text = getString(R.string.dollorSignWithPrice,
                        product?.price.toString())
                    Picasso.get()
                        .load(product?.image)
                        .placeholder(ContextCompat.getDrawable(requireContext(),R.drawable.img_placeholder)!!)
                        .into(binding.imgProductPic)






                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
    }
}