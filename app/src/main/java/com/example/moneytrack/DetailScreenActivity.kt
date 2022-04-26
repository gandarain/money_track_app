package com.example.moneytrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moneytrack.databinding.ActivityDetailScreenBinding

class DetailScreenActivity : AppCompatActivity() {
    private var binding: ActivityDetailScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()
    }

    /**
     * Setup the toolbar
     */
    private fun setupToolbar() {
        // set the toolbar
        setSupportActionBar(binding?.tollBarDetail)

        // setup the back button
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Detail"
        }
        // on press back
        binding?.tollBarDetail?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}