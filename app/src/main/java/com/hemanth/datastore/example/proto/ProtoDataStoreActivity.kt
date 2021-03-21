package com.hemanth.datastore.example.proto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.hemanth.datastore.example.R
import com.hemanth.datastore.example.databinding.ActivityProtoDatastoreBinding
import com.hemanth.datastore.example.proto.data.getFoodList
import com.hemanth.datastore.example.proto.model.FoodTaste
import com.hemanth.datastore.example.proto.model.FoodType
import kotlinx.coroutines.launch

class ProtoDataStoreActivity : AppCompatActivity() {

    private val foodPreferenceManager: FoodPreferenceManager by lazy {
        FoodPreferenceManager(applicationContext)
    }
    private val foodListAdapter by lazy { FoodListAdapter() }

    private val mBinding: ActivityProtoDatastoreBinding by lazy {
        ActivityProtoDatastoreBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        observePreferences()
        initFoodList()
        initViews()
        loadData()
    }

    private fun initViews() {
        mBinding.cgFoodTaste.setOnCheckedChangeListener { _, checkedId ->
            val taste = when (checkedId) {
                R.id.sweet -> FoodTaste.SWEET
                R.id.spicy -> FoodTaste.SPICY
                else -> null
            }

            lifecycleScope.launch { foodPreferenceManager.updateUserFoodTastePreference(taste) }
        }

        mBinding.cgFoodType.setOnCheckedChangeListener { _, checkedId ->
            val type = when (checkedId) {
                R.id.veg -> FoodType.VEG
                R.id.nonVeg -> FoodType.NON_VEG
                else -> null
            }

            lifecycleScope.launch { foodPreferenceManager.updateUserFoodTypePreference(type) }
        }
    }

    private fun initFoodList() {
        mBinding.recyclerView.adapter = foodListAdapter
    }

    private fun loadData() {
        foodListAdapter.submitList(getFoodList())
    }

    private fun observePreferences() {
        foodPreferenceManager.userFoodPreference.asLiveData().observe(this) {
            filterFoodList(it.type, it.taste)
        }
    }

    private fun filterFoodList(type: FoodType?, taste: FoodTaste?) {
        var filteredList = getFoodList()
        type?.let { foodType ->
            filteredList = filteredList.filter { it.type == foodType }
        }
        taste?.let { foodTaste ->
            filteredList = filteredList.filter { it.taste == foodTaste }
        }

        foodListAdapter.submitList(filteredList)

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No results!", Toast.LENGTH_SHORT).show()
        }

        updateViews(type, taste)
    }

    private fun updateViews(type: FoodType?, taste: FoodTaste?) {
        when (type) {
            FoodType.VEG -> mBinding.veg.isChecked = true
            FoodType.NON_VEG -> mBinding.nonVeg.isChecked = true
        }

        when (taste) {
            FoodTaste.SWEET -> mBinding.sweet.isChecked = true
            FoodTaste.SPICY -> mBinding.spicy.isChecked = true
        }
    }
}
