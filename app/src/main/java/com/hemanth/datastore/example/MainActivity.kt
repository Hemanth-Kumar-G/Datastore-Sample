package com.hemanth.datastore.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hemanth.datastore.example.databinding.ActivityMainBinding
import com.hemanth.datastore.example.preference.PreferenceDataStoreActivity
import com.hemanth.datastore.example.proto.ProtoDataStoreActivity

class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initViews()
    }

    private fun initViews() {
        mBinding.buttonPreference.setOnClickListener {
            startActivity(Intent(this@MainActivity, PreferenceDataStoreActivity::class.java))
        }

        mBinding.buttonProto.setOnClickListener {
            startActivity(Intent(this@MainActivity, ProtoDataStoreActivity::class.java))
        }
    }

}
