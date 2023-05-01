package com.faridsolgi.ecoshop.view

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.FragmentAddProductBinding
import com.faridsolgi.ecoshop.model.ProductResponse
import com.faridsolgi.ecoshop.model.utils.PRICE_REGEX
import com.faridsolgi.ecoshop.model.utils.URL_REGEX
import com.faridsolgi.ecoshop.model.utils.alertUser
import com.faridsolgi.ecoshop.view.dialog.AddProductResultDialog
import com.faridsolgi.ecoshop.viewmodel.AddProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
@AndroidEntryPoint
class AddProductFragment :
    BaseFragment<FragmentAddProductBinding>(FragmentAddProductBinding::inflate) {
    @Inject
    lateinit var viewModel: AddProductViewModel

    override fun fragmentBody() {

        binding.apply {
            tvTitle.editText?.doOnTextChanged { text, start, before, count ->
                tvTitle.error = null
            }
            tvDesc.editText?.doOnTextChanged { text, start, before, count ->
                tvDesc.error = null
            }
            tvCategory.editText?.doOnTextChanged { text, start, before, count ->
                tvCategory.error = null
            }
            tvPrice.editText?.doOnTextChanged { text, start, before, count ->
                tvPrice.error = null
            }
            tvImageUrl.editText?.doOnTextChanged { text, start, before, count ->
                tvImageUrl.error = null
            }
        }


        viewModel.apply {
            addNewProduct.observe(viewLifecycleOwner) {
                binding.apply {
                    tvTitle.editText?.text?.clear()
                    tvDesc.editText?.text?.clear()
                    tvCategory.editText?.text?.clear()
                    tvImageUrl.editText?.text?.clear()
                    tvPrice.editText?.text?.clear()
                }
                val bundle = Bundle()
                bundle.putSerializable(AddProductResultDialog.PRODUCT_KEY,it)
                parentFragmentManager.setFragmentResult(AddProductResultDialog.REQ_KEY,bundle)

                AddProductResultDialog().show(parentFragmentManager,
                    AddProductResultDialog::class.simpleName)
            }

            notifyMsg.observe(viewLifecycleOwner) {
                alertUser(binding.tvTitle, it)
            }
        }
        binding.btnAddProduct.setOnClickListener {
            if (checkInputValues()){
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.addNewProduct(ProductResponse(
                        binding.tvImageUrl.editText?.text.toString(),
                        binding.tvPrice.editText?.text.toString().toFloat(),
                        null,
                        binding.tvDesc.editText?.text.toString(),
                        null,
                        binding.tvTitle.editText?.text.toString(),
                        binding.tvCategory.editText?.text.toString()
                        ))
                }

            }
        }
        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(binding.root).popBackStack()
        }


    }

    private fun checkInputValues(): Boolean {
        try {
        if (binding.tvTitle.editText?.text.toString().isEmpty()) {
            binding.tvTitle.error =getString(R.string.please_fill_this_field)
            return false
        } else if (binding.tvDesc.editText?.text.toString().isEmpty()) {
            binding.tvDesc.error = getString(R.string.please_fill_this_field)
            return false
        } else if (binding.tvCategory.editText?.text.toString().isEmpty()) {
            binding.tvCategory.error = getString(R.string.please_fill_this_field)
            return false
        } else if (binding.tvImageUrl.editText?.text.toString().isEmpty()) {
            binding.tvImageUrl.error = getString(R.string.please_fill_this_field)
            return false
        } else if (!binding.tvImageUrl.editText?.text.toString().matches(URL_REGEX.toRegex())) {
            binding.tvImageUrl.error = getString(R.string.error_url_not_valid)
            return false
        } else if (binding.tvPrice.editText?.text.toString().isEmpty()) {
            binding.tvPrice.error = getString(R.string.please_fill_this_field)
            return false
        }else if (!binding.tvPrice.editText?.text.toString().matches(PRICE_REGEX.toRegex())) {
            binding.tvPrice.error = getString(R.string.please_fill_this_field)
            return false
        }
        return true
        }catch (e:Exception){
            e.printStackTrace()
            alertUser(binding.tvTitle,getString(R.string.system_error))
            return false
        }
    }
}