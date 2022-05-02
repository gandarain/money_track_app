package com.example.moneytrack

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class IncomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val content = inflater.inflate(R.layout.fragment_income, container, false) as ConstraintLayout

        btnNewOutcome(content)

        val incomeHistory = (activity?.applicationContext as CashFlowApp).db.cashFlowDao()
        loadIncomeHistory(incomeHistory, content)
        return content
    }

    private fun btnNewOutcome(content: View) {
        val btnNewOutcome: Button = content.findViewById(R.id.btnNewOutcome)
        btnNewOutcome.setOnClickListener {
            val intent = Intent(content.context, CreateScreenActivity::class.java)
            intent.putExtra(Constant.TYPE, Constant.INCOME)
            startActivity(intent)
        }

        val btnCreateNewIncome: Button = content.findViewById(R.id.btnCreateNewIncome)
        btnCreateNewIncome.setOnClickListener {
            val intent = Intent(content.context, CreateScreenActivity::class.java)
            intent.putExtra(Constant.TYPE, Constant.INCOME)
            startActivity(intent)
        }
    }

    private fun loadIncomeHistory(cashFlowDao: CashFlowDao, content: View) {
        lifecycleScope.launch {
            cashFlowDao.fetchAllIncome(Constant.INCOME).collect {
                val cashFlowList = ArrayList(it)
                setupHistoryCashFlow(cashFlowList, cashFlowDao, content)
            }
        }
    }

    private fun setupHistoryCashFlow(
        cashFlowList: ArrayList<CashFlowEntity>,
        cashFlowDao: CashFlowDao,
        content: View
    ) {
        val cashFlowAdapter = CashFlowAdapter(cashFlowList)
        val rvIncomeHistory: RecyclerView = content.findViewById(R.id.rvIncomeHistory)
        val llEmptyIncomeHistory: LinearLayout = content.findViewById(R.id.llEmptyIncomeHistory)

        if (cashFlowList.isNotEmpty()) {
            rvIncomeHistory.visibility = View.VISIBLE
            llEmptyIncomeHistory.visibility = View.GONE
            rvIncomeHistory.adapter = cashFlowAdapter
            rvIncomeHistory.layoutManager = LinearLayoutManager(content.context)
        } else {
            rvIncomeHistory.visibility = View.INVISIBLE
            llEmptyIncomeHistory.visibility = View.VISIBLE
        }
    }
}