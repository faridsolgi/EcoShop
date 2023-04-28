package com.faridsolgi.ecoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faridsolgi.ecoshop.databinding.ItemProductCategoryBinding
import com.google.android.material.chip.Chip
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ProductCategoryAdapter @Inject constructor(
    @ActivityContext val context: Context
) : RecyclerView.Adapter<ProductCategoryAdapter.ProductCategoryViewHolder>() {
    var categoriesList: List<String> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var _binding: ItemProductCategoryBinding? = null
    val binding get() = _binding!!
     var _setOnCategoriesItemClick : SetOnCategoriesItemClick? =null
   private val setOnCategoriesItemClick get() = _setOnCategoriesItemClick!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoryViewHolder {
        val inflater = LayoutInflater.from(context)
        _binding = ItemProductCategoryBinding.inflate(inflater, parent, false)
        return ProductCategoryViewHolder(binding,setOnCategoriesItemClick)
    }

    override fun onBindViewHolder(holder: ProductCategoryViewHolder, position: Int) {
        holder.onBind(categoriesList[position])
    }

    override fun getItemCount(): Int = categoriesList.size

    fun loadFirstCategoryItem(){
        try {
           setOnCategoriesItemClick.onProductCategoryClickListener(categoriesList[0])
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    class ProductCategoryViewHolder( val binding: ItemProductCategoryBinding,
                                    private val setOnCategoriesItemClick: SetOnCategoriesItemClick) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(category: String) {
            val chip = Chip(binding.root.context)
            chip.id = View.generateViewId()
            chip.text = category
            binding.chipProductCategory.isSingleSelection = true
            binding.chipProductCategory.addView(chip)
            chip.setOnClickListener {
                setOnCategoriesItemClick.onProductCategoryClickListener(category)
            }

        }
    }
    public interface SetOnCategoriesItemClick{
        fun onProductCategoryClickListener(category: String)
    }
}