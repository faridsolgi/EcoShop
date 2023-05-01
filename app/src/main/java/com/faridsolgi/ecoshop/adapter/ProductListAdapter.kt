package com.faridsolgi.ecoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.ItemProductBinding
import com.faridsolgi.ecoshop.model.ProductResponse
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ProductListAdapter @Inject constructor(@ActivityContext val context : Context)
    :RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    var _setOnProductClickListener : SetOnProductClickListener? =null
    private val setOnProductClickListener get() = _setOnProductClickListener!!
    var _setOnAddToCartClickListener : SetOnAddToCartClickListener? =null
    private val setOnAddToCartClickListener get() = _setOnAddToCartClickListener!!



     var productList  :List<ProductResponse> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    private var _binding :ItemProductBinding? =null
    val binding get() =  _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val inflater = LayoutInflater.from(context)
      _binding = ItemProductBinding.inflate(inflater,parent,false)
        return ProductListViewHolder(binding,setOnProductClickListener,setOnAddToCartClickListener)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.onBind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    class ProductListViewHolder(
        private val binding: ItemProductBinding,
        private val setOnProductClickListener: SetOnProductClickListener,
       private val setOnAddToCartClickListener: SetOnAddToCartClickListener
    )
        : RecyclerView.ViewHolder(binding.root){
        fun  onBind(productResponse: ProductResponse){
            try {
                binding.rvProductTitle.text = productResponse.title
                binding.price.text  = productResponse.price.toString()
                binding.rvProductdesc.text = productResponse.description
                binding.ratingBar.rating = (productResponse.rating?.rate ?: 0) as Float
                binding.tvCategory.text = productResponse.category.toString()
                if (!productResponse.image.isNullOrBlank()){
                    Picasso.get()
                        .load(productResponse.image)
                        .placeholder(ContextCompat.getDrawable(binding.root.context, R.drawable.img_placeholder)!!)
                        .into(binding.imageViewProductPic)

                }
                binding.addToCart.setOnClickListener {
                    setOnAddToCartClickListener.onAddToCartClick(productResponse)
                }
                binding.root.setOnClickListener {
                    setOnProductClickListener.onProductClick(productResponse)
                }

            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }
    interface SetOnAddToCartClickListener{
        fun onAddToCartClick(productResponse: ProductResponse)
    }
    interface SetOnProductClickListener{
        fun onProductClick(productResponse: ProductResponse)
    }

}
