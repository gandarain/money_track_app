package com.example.moneytrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moneytrack.databinding.ActivityCreateScreenBinding
import java.text.SimpleDateFormat
import java.util.*

class CreateScreenActivity : AppCompatActivity() {
    private var binding: ActivityCreateScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupToolbar()

        setupInput()

        onSubmit()
    }

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

    private fun setupInput() {
        if (intent.getStringExtra(Constant.AMOUNT) != null) {
            binding?.etAmount?.setText(intent.getStringExtra(Constant.AMOUNT))
        }

        if (intent.getStringExtra(Constant.TITLE) != null) {
            binding?.etTitle?.setText(intent.getStringExtra(Constant.TITLE))
        }

        if (intent.getStringExtra(Constant.DESCRIPTION) != null) {
            binding?.etDescription?.setText(intent.getStringExtra(Constant.DESCRIPTION))
        }
    }

    private fun generateDate(): String {
        val c = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(dateTime)
    }

    private fun onSubmit() {
        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.etTitle?.text
            val description = binding?.etDescription?.text
            val amount = binding?.etAmount?.text
            val date = generateDate()

            if (title.isNullOrEmpty() && description.isNullOrEmpty() && amount.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill all of the field!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Success submit the data!", Toast.LENGTH_SHORT).show()
                val intent = Intent(
                    this@CreateScreenActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }
    }
}