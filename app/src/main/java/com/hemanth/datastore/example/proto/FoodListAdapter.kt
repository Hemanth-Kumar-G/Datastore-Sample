package com.hemanth.datastore.example.proto

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemanth.datastore.example.databinding.ItemFoodBinding
import com.hemanth.datastore.example.proto.model.Food
import com.hemanth.datastore.example.proto.model.FoodTaste
import com.hemanth.datastore.example.proto.model.FoodType

class FoodListAdapter : ListAdapter<Food, FoodListAdapter.FoodItemViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodItemViewHolder =
        FoodItemViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: FoodItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FoodItemViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: Food) {
            binding.apply {
                tvFoodName.text = food.name
                tvFoodType.run {
                    text = food.type.name
                    setTextColor(
                        ContextCompat.getColor(
                            itemView.context, when (food.type) {
                                FoodType.NON_VEG -> android.R.color.holo_red_dark
                                FoodType.VEG -> android.R.color.holo_green_dark
                            }
                        )
                    )
                }

                tvFoodTaste.run {
                    text = food.taste.name
                    setTextColor(
                        ContextCompat.getColor(
                            itemView.context, when (food.taste) {
                                FoodTaste.SWEET -> android.R.color.holo_blue_light
                                FoodTaste.SPICY -> android.R.color.holo_orange_dark
                            }
                        )
                    )
                }
            }

        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Food>() {
            override fun areItemsTheSame(oldItem: Food, newItem: Food) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Food, newItem: Food) =
                oldItem.name == newItem.name
        }
    }
}
