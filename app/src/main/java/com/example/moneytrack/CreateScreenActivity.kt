package com.example.moneytrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.moneytrack.databinding.ActivityCreateScreenBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateScreenActivity : AppCompatActivity() {
    private var binding: ActivityCreateScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val cashFlowDao = (application as CashFlowApp).db.cashFlowDao()

        setupToolbar()

        setupInput()

        onSubmit(cashFlowDao)
    }

    private fun setupToolbar() {
        val type = intent.getStringExtra(Constant.TYPE)
        setSupportActionBar(binding?.toolBarCreate)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Create ${if (type == Constant.INCOME) "Income" else "Outcome"}"
        }

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

    private fun onSubmit(cashFlowDao: CashFlowDao) {
        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.etTitle?.text
            val description = binding?.etDescription?.text
            val amount = binding?.etAmount?.text
            val type = intent.getStringExtra(Constant.TYPE)
            val date = generateDate()

            if (title.isNullOrEmpty() && description.isNullOrEmpty() && amount.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill all of the field!", Toast.LENGTH_SHORT).show()
            } else {
                val isEdit: Boolean = intent.getBooleanExtra(Constant.IS_EDIT, false)
                val id: String? = intent.getStringExtra(Constant.ID)
                if (isEdit) {
                    editCashFlow(
                        id.toString(),
                        title.toString(),
                        description.toString(),
                        amount.toString(),
                        type.toString(),
                        date,
                        cashFlowDao
                    )
                } else {
                    submitToDatabase(
                        title.toString(),
                        description.toString(),
                        amount.toString(),
                        type.toString(),
                        date,
                        cashFlowDao
                    )
                }
            }
        }
    }

    private fun submitToDatabase(
        title: String,
        description: String,
        amount: String,
        type: String,
        date: String,
        cashFlowDao: CashFlowDao
    ) {
        lifecycleScope.launch {
            cashFlowDao.insert(
                CashFlowEntity(
                    title = title,
                    description = description,
                    type = type,
                    amount = amount.toInt(),
                    date = date,
                )
            )
            Toast.makeText(this@CreateScreenActivity, "Record Created!", Toast.LENGTH_SHORT).show()
            val intent = Intent(
                this@CreateScreenActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }

    private fun editCashFlow(
        id: String,
        title: String,
        description: String,
        amount: String,
        type: String,
        date: String,
        cashFlowDao: CashFlowDao
    ) {
        lifecycleScope.launch {
            cashFlowDao.update(
                CashFlowEntity(
                    id = id.toInt(),
                    title = title,
                    description = description,
                    type = type,
                    amount = amount.toInt(),
                    date = date,
                )
            )
            Toast.makeText(this@CreateScreenActivity, "Record Updated!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}