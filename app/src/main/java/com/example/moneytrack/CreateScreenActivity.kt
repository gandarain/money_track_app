package com.example.moneytrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moneytrack.databinding.ActivityCreateScreenBinding

class CreateScreenActivity : AppCompatActivity() {
    private var binding: ActivityCreateScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()
    }

    /**
     * Setup the toolbar
     */
    private fun setupToolbar() {
        // set the toolbar
        setSupportActionBar(binding?.toolBarCreate)

        // setup the back button
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Create"
        }
        // on press back
        binding?.toolBarCreate?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}