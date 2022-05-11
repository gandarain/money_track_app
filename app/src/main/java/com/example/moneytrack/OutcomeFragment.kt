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

class OutcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val content = inflater.inflate(R.layout.fragment_outcome, container, false) as ConstraintLayout

        btnNewOutcome(content)

        val incomeHistory = (activity?.applicationContext as CashFlowApp).db.cashFlowDao()
        loadOutcomeHistory(incomeHistory, content)
        loadTotalOutcome(incomeHistory, content)

        return content
    }

    private fun btnNewOutcome(content: View) {
        val btnNewOutcome: Button = content.findViewById(R.id.btnNewOutcome)
        btnNewOutcome.setOnClickListener {
            val intent = Intent(content.context, CreateScreenActivity::class.java)
            intent.putExtra(Constant.TYPE, Constant.OUTCOME)
            startActivity(intent)
        }

        val btnCreateNewOutcome: Button = content.findViewById(R.id.btnCreateNewOutcome)
        btnCreateNewOutcome.setOnClickListener {
            val intent = Intent(content.context, CreateScreenActivity::class.java)
            intent.putExtra(Constant.TYPE, Constant.OUTCOME)
            startActivity(intent)
        }
    }

    private fun loadOutcomeHistory(cashFlowDao: CashFlowDao, content: View) {
        lifecycleScope.launch {
            cashFlowDao.fetchAllIncome(Constant.OUTCOME).collect {
                val cashFlowList = ArrayList(it)
                setupHistoryOutcome(cashFlowList, cashFlowDao, content)
            }
        }
    }

    private fun setupHistoryOutcome(
        cashFlowList: ArrayList<CashFlowEntity>,
        cashFlowDao: CashFlowDao,
        content: View
    ) {
        val cashFlowAdapter = CashFlowAdapter(cashFlowList) { id ->
            navigateToDetailActivity(id, content)
        }
        val rvOutcomeHistory: RecyclerView = content.findViewById(R.id.rvOutcomeHistory)
        val llEmptyOutcomeHistory: LinearLayout = content.findViewById(R.id.llEmptyOutcomeHistory)

        if (cashFlowList.isNotEmpty()) {
            rvOutcomeHistory.visibility = View.VISIBLE
            llEmptyOutcomeHistory.visibility = View.GONE
            rvOutcomeHistory.adapter = cashFlowAdapter
            rvOutcomeHistory.layoutManager = LinearLayoutManager(content.context)
        } else {
            rvOutcomeHistory.visibility = View.INVISIBLE
            llEmptyOutcomeHistory.visibility = View.VISIBLE
        }
    }

    private fun loadTotalOutcome(cashFlowDao: CashFlowDao, content: View) {
        lifecycleScope.launch {
            val totalOutcome: Int? = cashFlowDao.calculateIncome(Constant.OUTCOME)
            setupTotalOutcome(totalOutcome, content)
        }
    }

    private fun setupTotalOutcome(
        totalOutcome: Int?,
        content: View
    ) {
        val tvTotalOutcome: TextView = content.findViewById(R.id.tvTotalOutcome)
        tvTotalOutcome.text =  Utils.convertToRupiah(totalOutcome ?: 0)
    }

    private fun navigateToDetailActivity(id: Int, content: View) {
        val intent = Intent(content.context, DetailScreenActivity::class.java)
        intent.putExtra(Constant.ID, id)
        startActivity(intent)
    }
}