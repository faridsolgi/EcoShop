package com.faridsolgi.ecoshop.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialog<T : ViewBinding>
    (private val layoutInflater: (LayoutInflater, ViewGroup?, Boolean) -> T) : DialogFragment() {
    companion object{
        private const val TAG = "BaseDialog"
    }
    private var _binding: T? = null
    val binding: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = layoutInflater(inflater,container,false)
        Log.i(TAG, "onCreateView: " + binding::class.java.simpleName)
        dialogBody()
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding =null
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
    abstract fun dialogBody()
}