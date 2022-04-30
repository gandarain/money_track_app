package com.example.moneytrack

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val content = inflater.inflate(R.layout.fragment_home, container, false) as ConstraintLayout
        btnNewOutcome(content)
        btnNewIncome(content)

        val exerciseHistoryDao = (activity?.applicationContext as CashFlowApp).db.cashFlowDao()
        loadCashFlow(exerciseHistoryDao)
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

    private fun loadCashFlow(cashFlowDao: CashFlowDao) {
        lifecycleScope.launch {
            cashFlowDao.fetchAllCashFlow().collect {
                val cashFlowList = ArrayList(it)
                Log.e("List ", cashFlowList.toString())
            }
        }
    }
}