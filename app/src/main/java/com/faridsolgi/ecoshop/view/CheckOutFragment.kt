package com.faridsolgi.ecoshop.view

import android.os.Build
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.Navigation
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.FragmentCheckOutBinding
import com.faridsolgi.ecoshop.model.CheckOut
import com.faridsolgi.ecoshop.model.utils.CreditCardNumberFormattingTextWatcher
import com.faridsolgi.ecoshop.model.utils.alertUser
import com.faridsolgi.ecoshop.model.utils.roundOffDecimal
import com.faridsolgi.ecoshop.view.dialog.CheckOutResultDialog
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class CheckOutFragment : BaseFragment<FragmentCheckOutBinding>(FragmentCheckOutBinding::inflate) {
    companion object {
        const val REQ_KEY = "CheckOutFragment_REQ_KEY"
        const val CHECKOUT_KEY = "CheckOutFragment_CHECKOUT_KEY_KEY"
    }

    override fun fragmentBody() {
        parentFragmentManager.setFragmentResultListener(
            REQ_KEY, viewLifecycleOwner
        ) { requestKey, result ->
            try {
                var checkOut: CheckOut?

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    checkOut =
                        result.getSerializable(CHECKOUT_KEY, CheckOut::class.java)
                } else {
                    checkOut = result.get(CHECKOUT_KEY) as CheckOut
                }

                binding.tvTotalPrice.text = getString(
                    R.string.dollorSignWithPrice,
                    roundOffDecimal(checkOut?.totalAmount).toString()
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }


        binding.btnCancel.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_container)
                .popBackStack()
        }

        binding.TilCardNumber.editText?.addTextChangedListener(CreditCardNumberFormattingTextWatcher())


        binding.btnCheckOut.setOnClickListener {
            if (checkInput()) {
                val bundle = Bundle()
                val checkOut = CheckOut(
                   binding.TilCardNumber.editText?.text.toString(),
                    binding.tvTotalPrice.text.toString().replace("$","").toFloat(),
                    "123456789"// it must generate from server
                )
                bundle.putSerializable(CheckOutResultDialog.CHECK_OUT_KEY,
                    checkOut)

                parentFragmentManager.setFragmentResult(CheckOutResultDialog.REQ_KEY,bundle)
                CheckOutResultDialog().show(parentFragmentManager,
                    CheckOutResultDialog::class.simpleName)


            }
        }
    }

    private fun checkInput(): Boolean {
        val CARD_NUMBER_LENGTH = 16
        try {
            if (binding.TilCardNumber.editText?.text?.isEmpty() == true
                    .or(binding.TilCardNumber.editText?.text?.isBlank() == true)
            ) {

                binding.TilCardNumber.error = getString(R.string.please_fill_this_field)
                return false
            } else if (binding.TilCardNumber.editText?.text?.toString()?.length!! < CARD_NUMBER_LENGTH) {
                binding.TilCardNumber.error = getString(R.string.please_fill_this_field)
                return false
            } else if (binding.TilCvv2.editText?.text?.isEmpty() == true
                    .or(binding.TilCvv2.editText?.text?.isBlank() == true)
            ) {
                binding.TilCvv2.error = getString(R.string.please_fill_this_field)
                return false
            } else if (binding.TilMonth.editText?.text?.isEmpty() == true
                    .or(binding.TilMonth.editText?.text?.isBlank() == true)
            ) {
                binding.TilMonth.error = getString(R.string.please_fill_this_field)
                return false
            } else if (binding.TilYear.editText?.text?.isEmpty() == true
                    .or(binding.TilYear.editText?.text?.isBlank() == true)
            ) {
                binding.TilYear.error = getString(R.string.please_fill_this_field)
                return false
            } else if (binding.TilEPass.editText?.text?.isEmpty() == true
                    .or(binding.TilEPass.editText?.text?.isBlank() == true)
            ) {
                binding.TilEPass.error = getString(R.string.please_fill_this_field)
                return false
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            alertUser(binding.TilCardNumber, getString(R.string.system_error))
            return false
        }

    }

}
