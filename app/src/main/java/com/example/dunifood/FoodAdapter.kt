package com.example.dunifood

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dunifood.databinding.ItemFoodBinding
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlin.math.log

class FoodAdapter(private  val  data :ArrayList<Food> , private val foodEvent:FoodEvent) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {





    inner class FoodViewHolder(private  val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root){

//        val imgMain = itemView.findViewById<ImageView>(R.id.item_img_main)
//        val txtSubject = itemView.findViewById<TextView>(R.id.item_txt_subject)
//        val txtCity = itemView.findViewById<TextView>(R.id.item_txt_city)
//        val txtPrice = itemView.findViewById<TextView>(R.id.item_txt_price)
//        val txtDistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
//        val ratingBar = itemView.findViewById<RatingBar>(R.id.item_rating_main)
//        val txtRating = itemView.findViewById<TextView>(R.id.item_txt_rating)



        @SuppressLint("SetTextI18n")
        fun  bindData(position: Int){

            binding.itemTxtSubject.text = data[position].txtSubject
            binding.itemTxtCity.text = data[position].txtCity
            binding.itemTxtDistance.text = data[position].txtDistance + "miles from you"
            binding.itemTxtPrice.text ="$" + data[position].txtPrice + "vip"
            binding.itemRatingMain.rating = data[position].rating
            binding.itemTxtRating.text ="(" + data[position].numOfRating.toString() + "Ratings)"


            Glide.with(binding.root.context)
                .load(data[position].urlImage)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(16 ,4 )))
                .into(binding.itemImgMain)


            itemView.setOnClickListener {
           foodEvent.onFoodClicked(data[adapterPosition] ,adapterPosition)
            }

            itemView.setOnLongClickListener {
                foodEvent.onFoodLongClick( data[adapterPosition] ,adapterPosition)
                true
            }

        }




    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        Log.v("testApp" , "onCreateViewHolder called")
      val  binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context) ,parent, false)

//    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food ,parent,false)
      return  FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
   return  data.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        Log.v("testApp" , "onBindViewHolder called")

        holder.bindData(position)



    }

    fun addFood(newFood :Food){
        //add food to list

        data.add(0 ,newFood)
        notifyItemInserted(0)
    }


    fun removeFood(oldFood:Food , oldPosition :Int){

        //remove item from list:
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)
    }

    fun  updateFood(newFood: Food ,position: Int){
        //update item from list

        data.set(position , newFood)
        notifyItemChanged(position)

    }

    fun setData(newList :ArrayList<Food>){

        //set new data to list

        data.clear()
        data.addAll(newList)

        notifyDataSetChanged()

    }


    interface FoodEvent{

        //مراحل ساخت اینترفیس
        //1.create interface in adapter
        //2.get an object of interface in args of adapter
        //3.fill(call) object of interface with your data
        //implementation in MainActivity
        fun onFoodClicked(food: Food , pos:Int)
        fun onFoodLongClick(food:Food , pos:Int)
    }



}