package com.example.moneytrack

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
    }

    private fun setupToolbar() {
        setSupportActionBar(binding?.tollBarDetail)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Detail"
        }

        binding?.tollBarDetail?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun loadCashFlowDetail(cashFlowDao: CashFlowDao) {
        val id = intent.getIntExtra(Constant.ID, Constant.EMPTY_ID)

        if (id != Constant.EMPTY_ID) {
            lifecycleScope.launch {
                cashFlowDao.fetchCashFlowById(id).collect {
                    setupDetail(it, cashFlowDao)
                }
            }
        }
    }

    private fun setupDetail(detailItem: CashFlowEntity, cashFlowDao: CashFlowDao) {
        binding?.tvDetailAmount?.text = Utils.convertToRupiah(detailItem.amount ?: 0)
        binding?.tvDetailType?.text = detailItem.type
        binding?.tvDetailTitle?.text = detailItem.title
        binding?.tvDetailDescription?.text = detailItem.description
        binding?.tvDetailDate?.text = detailItem.date

        setupButtonDelete(detailItem, cashFlowDao)
        setupEditButton(detailItem)
    }

    private fun setupButtonDelete(detailItem: CashFlowEntity, cashFlowDao: CashFlowDao) {
        binding?.btnDeleteItem?.setOnClickListener {
            deleteDialog(detailItem, cashFlowDao)
        }
    }

    private fun deleteDialog(detailItem: CashFlowEntity, cashFlowDao: CashFlowDao) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Item")
        builder.setMessage("Are you sure want to delete this item?")
        builder.setPositiveButton("Yes"){ dialogInterface, _ ->
            lifecycleScope.launch {
                cashFlowDao.delete(detailItem)
                Toast.makeText(this@DetailScreenActivity, "Item deleted!", Toast.LENGTH_SHORT).show()
                dialogInterface.dismiss()
                val intent = Intent(
                    this@DetailScreenActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }
        builder.setNegativeButton("No"){ dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun setupEditButton(detailItem: CashFlowEntity) {
        binding?.btnEditItem?.setOnClickListener {
            val intent = Intent(
                this@DetailScreenActivity,
                CreateScreenActivity::class.java
            )
            intent.putExtra(Constant.ID, detailItem.id.toString())
            intent.putExtra(Constant.TYPE, detailItem.type)
            intent.putExtra(Constant.TITLE, detailItem.title)
            intent.putExtra(Constant.DESCRIPTION, detailItem.description)
            intent.putExtra(Constant.AMOUNT, detailItem.amount.toString())
            intent.putExtra(Constant.DATE, detailItem.date)
            intent.putExtra(Constant.IS_EDIT, true)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}