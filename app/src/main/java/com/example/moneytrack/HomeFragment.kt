package com.example.moneytrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val content = inflater.inflate(R.layout.fragment_home, container, false) as ConstraintLayout
        btnNewOutcome(content)
        btnNewIncome(content)

        val cashFlowDao = (activity?.applicationContext as CashFlowApp).db.cashFlowDao()
        loadCashFlow(cashFlowDao, content)
        return content
    }

    private fun btnNewOutcome(content: View) {
        val btnNewOutcome: CardView = content.findViewById(R.id.btnNewOutcome)
        btnNewOutcome.setOnClickListener {
            val intent = Intent(content.context, CreateScreenActivity::class.java)
            intent.putExtra(Constant.TYPE, Constant.OUTCOME)
            startActivity(intent)
        }
    }

    private fun btnNewIncome(content: View) {
        val btnNewIncome: CardView = content.findViewById(R.id.btnNewIncome)
        btnNewIncome.setOnClickListener {
            val intent = Intent(content.context, CreateScreenActivity::class.java)
            intent.putExtra(Constant.TYPE, Constant.INCOME)
            startActivity(intent)
        }
    }

    private fun loadCashFlow(cashFlowDao: CashFlowDao, content: View) {
        lifecycleScope.launch {
            cashFlowDao.fetchAllCashFlow().collect {
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
        val rvHistory: RecyclerView = content.findViewById(R.id.rvHistory)
        val tvEmptyHistory: TextView = content.findViewById(R.id.tvEmptyHistory)

        if (cashFlowList.isNotEmpty()) {
            rvHistory.visibility = View.VISIBLE
            tvEmptyHistory.visibility = View.GONE
            rvHistory.adapter = cashFlowAdapter
            rvHistory.layoutManager = LinearLayoutManager(content.context)
        } else {
            rvHistory.visibility = View.INVISIBLE
            tvEmptyHistory.visibility = View.VISIBLE
        }
    }
}