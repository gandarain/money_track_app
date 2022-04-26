package com.example.moneytrack

import android.content.Intent
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

        btnEditHandler()
    }

    private fun btnEditHandler() {
        binding?.btnEdit?.setOnClickListener {
            val intent = Intent(
                this@DetailScreenActivity,
                CreateScreenActivity::class.java
            )
            startActivity(intent)
        }
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