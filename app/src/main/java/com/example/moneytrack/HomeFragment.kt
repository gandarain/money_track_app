package com.example.moneytrack

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
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
        loadTotalTransaction(cashFlowDao, content)
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
            cashFlowDao.fetchRecentTransaction().collect {
                val cashFlowList = ArrayList(it)
                setupHistoryCashFlow(cashFlowList, cashFlowDao, content)
            }
        }
    }

    private fun loadTotalTransaction(cashFlowDao: CashFlowDao, content: View) {
        lifecycleScope.launch {
            val totalIncome: Int? = cashFlowDao.calculateIncome(Constant.INCOME)
            val totalOutcome: Int? = cashFlowDao.calculateIncome(Constant.OUTCOME)
            setupTotalTransaction(totalIncome, totalOutcome, content)
        }
    }

    private fun setupHistoryCashFlow(
        cashFlowList: ArrayList<CashFlowEntity>,
        cashFlowDao: CashFlowDao,
        content: View
    ) {
        val cashFlowAdapter = CashFlowAdapter(cashFlowList) { id ->
            navigateToDetailActivity(id, content)
        }
        val rvHistory: RecyclerView = content.findViewById(R.id.rvHistory)
        val llEmptyHistory: LinearLayout = content.findViewById(R.id.llEmptyHistory)

        if (cashFlowList.isNotEmpty()) {
            rvHistory.visibility = View.VISIBLE
            llEmptyHistory.visibility = View.GONE
            rvHistory.adapter = cashFlowAdapter
            rvHistory.layoutManager = LinearLayoutManager(content.context)
        } else {
            rvHistory.visibility = View.INVISIBLE
            llEmptyHistory.visibility = View.VISIBLE
        }
    }

    private fun setupTotalTransaction(
        totalIncome: Int?,
        totalOutcome: Int?,
        content: View
    ) {
        val tvIncomeTotal: TextView = content.findViewById(R.id.tvIncomeTotal)
        val tvOutcomeTotal: TextView = content.findViewById(R.id.tvOutcomeTotal)
        val tvTotalCashflow: TextView = content.findViewById(R.id.tvTotalCashflow)

        tvIncomeTotal.text =  Utils.convertToRupiah(totalIncome ?: 0)
        tvOutcomeTotal.text = Utils.convertToRupiah(totalOutcome ?: 0)
        tvTotalCashflow.text = Utils.convertToRupiah((totalIncome ?: 0) - (totalOutcome ?: 0))

        setupCardViewSummaryTransaction(content, totalIncome?: 0, totalOutcome?: 0)
    }

    private fun navigateToDetailActivity(id: Int, content: View) {
        val intent = Intent(content.context, DetailScreenActivity::class.java)
        intent.putExtra(Constant.ID, id)
        startActivity(intent)
    }

    private fun setupCardViewSummaryTransaction(content: View, totalIncome: Int, totalOutcome: Int) {
        val cvSummaryTransaction: CardView? = content.findViewById(R.id.cvSummaryTransaction)
        cvSummaryTransaction?.setOnClickListener {
            val intent = Intent(content.context, SummaryActivity::class.java)
            intent.putExtra(Constant.INCOME, totalIncome)
            intent.putExtra(Constant.OUTCOME, totalOutcome)
            startActivity(intent)
        }
    }
}