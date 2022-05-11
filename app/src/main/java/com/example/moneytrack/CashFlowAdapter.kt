package com.example.moneytrack

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.moneytrack.databinding.ItemHistoryBinding

class CashFlowAdapter(
    private val cashFlowList: ArrayList<CashFlowEntity>,
    private val detailScreen: (id: Int) -> Unit
): RecyclerView.Adapter<CashFlowAdapter.MainViewHolder>() {
    inner class MainViewHolder(private val itemBinding: ItemHistoryBinding): RecyclerView.ViewHolder(itemBinding.root) {
        val imageView = itemBinding.ivImage
        val textViewTitle = itemBinding.tvTitle
        val textViewDate = itemBinding.tvDate
        val textViewAmount = itemBinding.tvAmount
        val linerLayoutItemHistory = itemBinding.llItemHistory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val cashFlow = cashFlowList[position]
        val context = holder.itemView.context

        holder.textViewTitle.text = cashFlow.title
        holder.textViewDate.text = cashFlow.date

        if (cashFlow.type == Constant.OUTCOME) {
            holder.textViewAmount.text = "- ${Utils.convertToRupiah(cashFlow.amount)}"
            holder.textViewAmount.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.redColor
                )
            )
            holder.imageView.setImageResource(R.drawable.money_out)
        } else {
            holder.textViewAmount.text = "+ ${Utils.convertToRupiah(cashFlow.amount)}"
            holder.textViewAmount.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.greenColor
                )
            )
            holder.imageView.setImageResource(R.drawable.money_in)
        }

        holder.linerLayoutItemHistory.setOnClickListener {
            detailScreen.invoke(cashFlow.id)
        }
    }

    override fun getItemCount(): Int {
        return cashFlowList.size
    }

}