package com.faridsolgi.ecoshop.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.adapter.ShoppingCartAdapter
import com.faridsolgi.ecoshop.databinding.FragmentShoppingCartBinding
import com.faridsolgi.ecoshop.model.CheckOut
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import com.faridsolgi.ecoshop.model.utils.initRecycler
import com.faridsolgi.ecoshop.model.utils.roundOffDecimal
import com.faridsolgi.ecoshop.viewmodel.ShoppingCartViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class ShoppingCartFragment
    : BaseFragment<FragmentShoppingCartBinding>(FragmentShoppingCartBinding::inflate),
    ShoppingCartAdapter.SetOnAddQuantityClickListener,
    ShoppingCartAdapter.SetOnRemoveQuantityClickListener{
    @Inject
    lateinit var viewModel: ShoppingCartViewModel

    @Inject
    lateinit var shoppingCartAdapter: ShoppingCartAdapter

    var totalAmount: Float = 0f
    var installmentAmount = 0f
    override fun fragmentBody() {

        binding.radioGLoanPercent.setOnCheckedChangeListener { group, checkedId ->
            var loanPercentage = 0
            var loanInterest = 0f
            when(checkedId){
                    R.id.radioBtn3Month -> {
                        loanPercentage = 5
                        loanInterest = totalAmount / 100 * loanPercentage
                        installmentAmount = (totalAmount + loanInterest) / 3
                    }
                    R.id.radioBtn6Month -> {
                        loanPercentage = 10
                        loanInterest = totalAmount / 100 * loanPercentage
                        installmentAmount = (totalAmount + loanInterest) / 6
                    }
                    R.id.radioBtn12Month -> {
                        loanPercentage = 20
                        loanInterest = totalAmount / 100 * loanPercentage
                        installmentAmount = (totalAmount + loanInterest) / 12
                    }
            }
            setupPieChart(totalAmount,loanInterest)
        }

        binding.rvShoppingCart.initRecycler(
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false),
            shoppingCartAdapter
        )
        shoppingCartAdapter._setOnAddQuantityClickListener = this
        shoppingCartAdapter._setOnRemoveQuantityClickListener = this


        viewModel.apply {
            cartListLiveData.observe(viewLifecycleOwner) {
                shoppingCartAdapter.shoppingCartList = it
                if (it.isNotEmpty()) {
                    binding.clNoItem.visibility = View.GONE
                } else {
                    binding.clNoItem.visibility = View.VISIBLE
                }
            }
            totalCartAmountLiveData.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.tvTotalAmount.text =
                        getString(R.string.priceSignWithDollor, roundOffDecimal(it).toString())
                    totalAmount = it
                    //check 3 month loan AS default
                    binding.radioGLoanPercent.clearCheck()
                    binding.radioBtn3Month.isChecked=true

                }

            }

        }
        activity?.onBackPressedDispatcher
            ?.addCallback(viewLifecycleOwner,
                object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_container)
                    .navigate(R.id.action_shoppingCartFragment_to_mainFragment)
            }
        })
        binding.btnAddOne.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_container)
                .navigate(R.id.action_shoppingCartFragment_to_mainFragment)
        }
        binding.btnProceedCheckout.setOnClickListener {
            when (binding.radioGPaymentType.checkedRadioButtonId) {
                R.id.radioBtnLoan -> {
                    //calculate loan interests
                    val bundle = Bundle()
                    bundle.putSerializable(
                        CheckOutFragment.CHECKOUT_KEY,
                        CheckOut(totalAmount = installmentAmount)
                    )
                    setFragmentResult(CheckOutFragment.REQ_KEY, bundle)
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_shoppingCartFragment_to_checkOutFragment)

                }
                R.id.radioBtnCash -> {
                    val bundle = Bundle()
                    bundle.putSerializable(
                        CheckOutFragment.CHECKOUT_KEY,
                        CheckOut(totalAmount = totalAmount)
                    )
                    setFragmentResult(CheckOutFragment.REQ_KEY, bundle)
                    Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_container)
                        .navigate(R.id.action_shoppingCartFragment_to_checkOutFragment)
                }
            }
        }




        binding.radioGPaymentType.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radioBtnCash -> {
                    binding.clCashPayment.visibility = View.VISIBLE
                    binding.clLoanPayment.visibility = View.GONE
                }
                R.id.radioBtnLoan -> {
                    binding.clCashPayment.visibility = View.GONE
                    binding.clLoanPayment.visibility = View.VISIBLE

                }
            }
        }
    }

    override fun onAddQuantityClick(shoppingCart: ShoppingCart) {
        lifecycleScope.launch {
            viewModel.addToCart(shoppingCart)
        }
    }

    override fun onRemoveQuantityClick(shoppingCart: ShoppingCart) {
        lifecycleScope.launch {
            viewModel.removeCartQuantity(shoppingCart)
        }

    }


    private fun setupPieChart(price: Float, interests: Float) {
        val pieChart = binding.chartInterest
        binding.tvInterestChart.text =
            getString(R.string.dollorSignWithPrice, roundOffDecimal(interests).toString())
        binding.tvTotalPriceChart.text =
            getString(R.string.dollorSignWithPrice, roundOffDecimal(price).toString())
        //pupulating list of PieEntires
        val pieEntires: MutableList<PieEntry> = ArrayList()
        pieEntires.add(PieEntry(price, "total price"))
        pieEntires.add(PieEntry(interests, "interests"))

        val dataSet = PieDataSet(pieEntires, "")
        dataSet.setColors(
            MaterialColors.getColor(
                binding.root,
                com.google.android.material.R.attr.colorPrimary
            ),
            MaterialColors.getColor(
                binding.root,
                com.google.android.material.R.attr.colorSecondary
            )
        )
        dataSet.setDrawValues(false)

        val data = PieData(dataSet)
        //Get the chart
        pieChart.data = data
        pieChart.invalidate()

        pieChart.setDrawEntryLabels(false)
        pieChart.contentDescription = ""
        pieChart.description.isEnabled = false
        //pieChart.setDrawMarkers(true);
        //pieChart.setMaxHighlightDistance(34);
        pieChart.setEntryLabelTextSize(12f)

        pieChart.holeRadius = 75f

        //legend attributes
        val legend: Legend = pieChart.legend
        legend.form = Legend.LegendForm.CIRCLE
        legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.xOffset = 70f
        legend.yOffset = -30f
        legend.yEntrySpace = 16f
        legend.textSize = 14f
        legend.formSize = 20f
        legend.formToTextSpace = 2f
    }



}