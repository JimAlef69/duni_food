package com.example.dunifood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dunifood.databinding.ActivityMainBinding
import com.example.dunifood.databinding.DialogAddNewItemBinding
import com.example.dunifood.databinding.DialogDeleteItemBinding
import com.example.dunifood.databinding.DialogUpdateNewItemBinding

const val TAG_MAIN_ACTIVITY = "activityMain"

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvent {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: FoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Log.v(TAG_MAIN_ACTIVITY,"tabe call shode amooo")

        //how to use recycler view:
        // 1.create view of recycleView in activity_main.xml
        //2.create item for recycleView
        //3.create adapter and viewHolder for recycleView
        //4.set adapter to recycleView and set layout manager


        val foodList = arrayListOf(
            Food(
                "Hamburger",
                "15",
                "3",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                20,
                4.5f
            ),
            Food(
                "Grilled fish",
                "20",
                "2.1",
                "Tehran, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                10,
                4f
            ),
            Food(
                "Lasania",
                "40",
                "1.4",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                30,
                2f
            ),
            Food(
                "pizza",
                "10",
                "2.5",
                "Zahedan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                80,
                1.5f
            ),
            Food(
                "Sushi",
                "20",
                "3.2",
                "Mashhad, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                200,
                3f
            ),
            Food(
                "Roasted Fish",
                "40",
                "3.7",
                "Jolfa, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                50,
                3.5f
            ),
            Food(
                "Fried chicken",
                "70",
                "3.5",
                "NewYork, USA",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                70,
                2.5f
            ),
            Food(
                "Vegetable salad",
                "12",
                "3.6",
                "Berlin, Germany",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                40,
                4.5f
            ),
            Food(
                "Grilled chicken",
                "10",
                "3.7",
                "Beijing, China",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                15,
                5f
            ),
            Food(
                "Baryooni",
                "16",
                "10",
                "Ilam, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                28,
                4.5f
            ),
            Food(
                "Ghorme Sabzi",
                "11.5",
                "7.5",
                "Karaj, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                27,
                5f
            ),
            Food(
                "Rice",
                "12.5",
                "2.4",
                "Shiraz, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                35,
                2.5f
            ),
        )
        Log.v(TAG_MAIN_ACTIVITY , "tedade ghaza ha barabar ast ba: ${foodList.size}")
        myAdapter = FoodAdapter(foodList.clone() as ArrayList<Food>, this)
        binding.RecycleMain.adapter = myAdapter
        binding.RecycleMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.btnAddNewFood.setOnClickListener {
            Log.v(TAG_MAIN_ACTIVITY ,"btn add clicked")
            val dialog = AlertDialog.Builder(this).create()
            val dialogbinding = DialogAddNewItemBinding.inflate(layoutInflater)
            dialog.setView(dialogbinding.root)
            dialog.setCancelable(true)
            dialog.show()
            dialogbinding.dialogBtnDone.setOnClickListener {


                if (
                    dialogbinding.dialodEdtFoodName.editText?.length()!! > 0 &&
                    dialogbinding.dialodEdtCityCame.editText?.length()!! > 0 &&
                    dialogbinding.dialogEdtDistance.editText?.length()!! > 0 &&
                    dialogbinding.dialogEdtPrice.editText?.length()!! > 0
                ) {
                    val texName = dialogbinding.dialodEdtFoodName.editText?.text.toString()
                    val txtPrice = dialogbinding.dialogEdtPrice.editText?.text.toString()
                    val txtDistance = dialogbinding.dialogEdtDistance.editText?.text.toString()
                    val txtCity = dialogbinding.dialodEdtCityCame.editText?.text.toString()
                    val txtRatingNumber: Int = (1..150).random()
                    val ratingBarStar: Float = (1..5).random().toFloat()
                    val randomFromPic = (0..11).random()
                    val urlImage = foodList[randomFromPic].urlImage
                    val newFood = Food(
                        texName,
                        txtPrice,
                        txtDistance,
                        txtCity,
                        urlImage,
                        txtRatingNumber,
                        ratingBarStar
                    )
                    myAdapter.addFood(newFood)
                    binding.RecycleMain.scrollToPosition(0)
                    dialog.dismiss()

                } else {
                    Toast.makeText(this, "لطفا مقادیر را وارد کنید", Toast.LENGTH_SHORT).show()
           /*         Toast.makeText(this, "لطفا مقادیر را وارد کنید", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "لطفا مقادیر را وارد کنید", Toast.LENGTH_SHORT).show()*/


                }


            }


        }

        binding.edtSearch.addTextChangedListener {editTextInput ->


            if (editTextInput!!.isNotEmpty()){

                //filter data 'h'

                val cloneList = foodList.clone() as ArrayList<Food>

                val filterList = cloneList.filter { foodItem ->

                     (foodItem.txtSubject.contains(editTextInput))
                }
                myAdapter.setData(filterList as ArrayList<Food>)
            }else{
                //show all data

                myAdapter.setData(foodList.clone() as ArrayList<Food>)

            }
        }



    }

    override fun onFoodClicked(food: Food, pos: Int) {
        Log.v(TAG_MAIN_ACTIVITY , "click on food k barabar ast ba:${food.txtSubject}")

        val dialog = AlertDialog.Builder(this).create()
        val updateDialogBinding = DialogUpdateNewItemBinding.inflate(layoutInflater)

        dialog.setView(updateDialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()
        updateDialogBinding.dialodEdtFoodName.editText?.setText(food.txtSubject)
        updateDialogBinding.dialodEdtCityCame.editText?.setText(food.txtCity)
        updateDialogBinding.dialogEdtPrice.editText?.setText(food.txtPrice)
        updateDialogBinding.dialogEdtDistance.editText?.setText(food.txtDistance)

        updateDialogBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

        updateDialogBinding.dialogUpdateBtnDone.setOnClickListener {


            //update item:
            if (
                updateDialogBinding.dialodEdtFoodName.editText?.length()!! > 0 &&
                updateDialogBinding.dialodEdtCityCame.editText?.length()!! > 0 &&
                updateDialogBinding.dialogEdtDistance.editText?.length()!! > 0 &&
                updateDialogBinding.dialogEdtPrice.editText?.length()!! > 0
            ) {

                val texName = updateDialogBinding.dialodEdtFoodName.editText?.text.toString()
                val txtPrice = updateDialogBinding.dialogEdtPrice.editText?.text.toString()
                val txtDistance = updateDialogBinding.dialogEdtDistance.editText?.text.toString()
                val txtCity = updateDialogBinding.dialodEdtCityCame.editText?.text.toString()
                //create new food to add to recycleView
                val newFood = Food(
                    texName,
                    txtPrice,
                    txtDistance,
                    txtCity,
                    food.urlImage,
                    food.numOfRating,
                    food.rating
                )

                myAdapter.updateFood(newFood, pos)
                dialog.dismiss()
            }else{
                Toast.makeText(this, "لطفا همه ی مقادیر را وارد کنید", Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun onFoodLongClick(food: Food, pos: Int) {
        val dialog = AlertDialog.Builder(this).create()
        val dialogBinding = DialogDeleteItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()


        dialogBinding.itemBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.itemBtnSure.setOnClickListener {
            dialog.dismiss()

            myAdapter.removeFood(food, pos)

        }
    }


}