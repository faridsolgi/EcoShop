package com.faridsolgi.ecoshop.view.dialog

import android.os.Build
import androidx.fragment.app.activityViewModels
import com.faridsolgi.ecoshop.databinding.DialogCheckOutResultBinding
import com.faridsolgi.ecoshop.model.CheckOut
import com.faridsolgi.ecoshop.model.utils.roundOffDecimal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutResultDialog :
    BaseDialog<DialogCheckOutResultBinding>(DialogCheckOutResultBinding::inflate) {

    companion object {
        const val REQ_KEY = "CheckOutResultDialogReqKey"
        const val CHECK_OUT_KEY = "CHECK_OUT_KEY"
    }


    override fun dialogBody() {

        parentFragmentManager.setFragmentResultListener(
            REQ_KEY, viewLifecycleOwner
        ) { requestKey, result ->
            try {


                var checkOut: CheckOut?

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    checkOut = result.getSerializable(CHECK_OUT_KEY, CheckOut::class.java)
                } else {
                    checkOut = result.get(CHECK_OUT_KEY) as CheckOut
                }

                binding.tvCardNumber.text =  checkOut?.cardNumber.toString().substring(0,10)+
                        checkOut?.cardNumber.toString().substring(10).replace("\\d".toRegex(),
                    "*")
                binding.tvTotalAmount.text = roundOffDecimal(checkOut?.totalAmount).toString()
                binding.tvTrackingCode.text = checkOut?.trackingCode.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.btnCompleteTransaction.setOnClickListener {
            dismiss()
        }

    }

}