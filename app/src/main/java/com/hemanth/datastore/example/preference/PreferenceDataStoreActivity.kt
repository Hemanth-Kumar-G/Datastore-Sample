package com.hemanth.datastore.example.preference

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.hemanth.datastore.example.R
import com.hemanth.datastore.example.databinding.ActivityPreferenceDataStoreBinding
import kotlinx.coroutines.launch

class PreferenceDataStoreActivity : AppCompatActivity() {

    private lateinit var settingsManager: SettingsManager

    private val mBinding: ActivityPreferenceDataStoreBinding by lazy {
        ActivityPreferenceDataStoreBinding.inflate(layoutInflater)
    }

    private var isDarkMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        settingsManager = SettingsManager(context = applicationContext)
        observeUiPreferences()
    }

    private fun observeUiPreferences() {
        settingsManager.uiModeFlow.asLiveData().observe(this) { uiMode ->
            when (uiMode) {
                UiMode.LIGHT -> onLightMode()
                UiMode.DARK -> onDarkMode()
                else -> Unit
            }
        }
    }

    private fun onDarkMode() {
        isDarkMode = true
        mBinding.root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
        mBinding.ivPreference.setImageResource(R.drawable.ic_moon)

    }

    private fun onLightMode() {
        isDarkMode = false
        mBinding.root.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black))
        mBinding.ivPreference.setImageResource(R.drawable.ic_sun)

    }

    fun onImageClick(v: View) {
        lifecycleScope.launch {
            when (isDarkMode) {
                true -> settingsManager.setUiMode(UiMode.LIGHT)
                false -> settingsManager.setUiMode(UiMode.DARK)
            }
        }
    }
}
