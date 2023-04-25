package com.faridsolgi.samplestore.view
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>
    (private val layoutInflater: (LayoutInflater, ViewGroup?, Boolean) -> T) : Fragment() {
    companion object{
        private const val TAG = "BaseFragment"
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
        fragmentBody()
        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }

    abstract fun fragmentBody()
}