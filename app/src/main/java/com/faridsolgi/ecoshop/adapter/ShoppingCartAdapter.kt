package com.faridsolgi.ecoshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.faridsolgi.ecoshop.R
import com.faridsolgi.ecoshop.databinding.ItemShoppingCartBinding
import com.faridsolgi.ecoshop.model.room.entity.ShoppingCart
import com.faridsolgi.ecoshop.model.utils.roundOffDecimal
import com.squareup.picasso.Picasso
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ShoppingCartAdapter @Inject constructor(@ActivityContext val context:Context)
    :RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>() {
     var shoppingCartList :List<ShoppingCart> = mutableListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    private var _binding : ItemShoppingCartBinding? =null
   private val binding get() =  _binding!!

    var _setOnAddQuantityClickListener :SetOnAddQuantityClickListener? = null
    private val setOnAddQuantityClickListener get() = _setOnAddQuantityClickListener!!

     var _setOnRemoveQuantityClickListener :SetOnRemoveQuantityClickListener? =null
    private val setOnRemoveQuantityClickListener get() = _setOnRemoveQuantityClickListener!!






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val inflater =LayoutInflater.from(context)
        _binding = ItemShoppingCartBinding.inflate(inflater,parent,false)
        return ShoppingCartViewHolder(binding,
            setOnAddQuantityClickListener,
            setOnRemoveQuantityClickListener)
    }

    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        holder.onBind(shoppingCartList[position])
    }

    override fun getItemCount(): Int  = shoppingCartList.size

    class ShoppingCartViewHolder @Inject constructor(
        private val binding: ItemShoppingCartBinding,
        private val setOnAddQuantityClickListener: SetOnAddQuantityClickListener,
        private val setOnRemoveQuantityClickListener: SetOnRemoveQuantityClickListener,

    )
        : RecyclerView.ViewHolder(binding.root){
        fun onBind(shoppingCart: ShoppingCart){
            try {
                binding.tvProductName.text = shoppingCart.name
                binding.tvProductPrice.text = itemView.context.getString(R.string.dollorSignWithPrice,shoppingCart.price.toString())
                val totalItemPrice = shoppingCart.price?.times(shoppingCart.count)
                binding.tvTotalPrice.text =
                    itemView.context.getString(R.string.dollorSignWithPrice,
                    roundOffDecimal(totalItemPrice).toString())
                binding.txtInpLayQuantity.editText?.setText(shoppingCart.count.toString())

                binding.btnAddQuantity.setOnClickListener {
                  setOnAddQuantityClickListener.onAddQuantityClick(shoppingCart)
                }
                binding.btnRemoveQuantity.setOnClickListener {
                    if (shoppingCart.count>0)
                  setOnRemoveQuantityClickListener.onRemoveQuantityClick(shoppingCart)
                }
                Picasso.get()
                    .load(shoppingCart.imageUrl)
                    .placeholder(ContextCompat.getDrawable(binding.root.context, R.drawable.img_placeholder)!!)
                    .into(binding.imgProductPic)

            }catch (e:Exception){
                e.printStackTrace()
            }
        }

    }


    interface SetOnAddQuantityClickListener{
        fun onAddQuantityClick(shoppingCart: ShoppingCart)
    }
    interface SetOnRemoveQuantityClickListener{
        fun onRemoveQuantityClick(shoppingCart: ShoppingCart)
    }

}