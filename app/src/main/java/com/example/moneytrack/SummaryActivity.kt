package com.example.moneytrack

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.moneytrack.databinding.ActivitySummaryBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class SummaryActivity : AppCompatActivity() {
    private var binding: ActivitySummaryBinding? = null
    private lateinit var pieChart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        pieChart = binding?.pieChart!!

        setupToolbar()

        initPieChart()

        val outcome: Float = intent.getIntExtra(Constant.OUTCOME, 0).toFloat()
        val income: Float = intent.getIntExtra(Constant.INCOME, 0).toFloat()

        if (outcome > 0 && income > 0) {
            setDataToPieChart(outcome, income)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding?.toolBarSummary)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Summary Transaction"
        }

        binding?.toolBarSummary?.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initPieChart() {
        pieChart.setUsePercentValues(true)
        pieChart.description.text = "Summary Transaction"
        //hollow pie chart
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)
        //adding padding
        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
    }

    private fun setDataToPieChart(outcome: Float, income: Float) {
        pieChart.setUsePercentValues(true)
        val dataEntries = ArrayList<PieEntry>()

        dataEntries.add(PieEntry(outcome, "Outcome"))
        dataEntries.add(PieEntry(income, "Income"))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(ContextCompat.getColor(this@SummaryActivity, R.color.redColor))
        colors.add(ContextCompat.getColor(this@SummaryActivity, R.color.greenColor))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "Summary Transaction"

        pieChart.invalidate()

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}