package com.example.moneytrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.moneytrack.databinding.ActivityDetailScreenBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailScreenActivity : AppCompatActivity() {
    private var binding: ActivityDetailScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val cashFlowDao = (application as CashFlowApp).db.cashFlowDao()
        loadCashFlowDetail(cashFlowDao)

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

    private fun loadCashFlowDetail(cashFlowDao: CashFlowDao) {
        val id = intent.getIntExtra(Constant.ID, Constant.EMPTY_ID)

        if (id != Constant.EMPTY_ID) {
            lifecycleScope.launch {
                cashFlowDao.fetchCashFlowById(id).collect {
                    setupDetail(it)
                }
            }
        }
    }

    private fun setupDetail(detailItem: CashFlowEntity) {
        binding?.tvDetailType?.text = detailItem.type
        binding?.tvDetailTitle?.text = detailItem.title
        binding?.tvDetailDescription?.text = detailItem.description
        binding?.tvDetailDate?.text = detailItem.date
    }
}