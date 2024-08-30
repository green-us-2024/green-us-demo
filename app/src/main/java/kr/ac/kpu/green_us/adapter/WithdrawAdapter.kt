package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R
import kr.ac.kpu.green_us.common.dto.Withdraw


class WithdrawAdapter(private val withdrawList: List<Withdraw>) :
    RecyclerView.Adapter<WithdrawAdapter.WithdrawViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WithdrawViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_withdraw, parent, false)
        return WithdrawViewHolder(view)
    }

    override fun onBindViewHolder(holder: WithdrawViewHolder, position: Int) {
        val withdraw = withdrawList[position]
        holder.bind(withdraw)
    }

    override fun getItemCount(): Int {
        return withdrawList.size
    }

    class WithdrawViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountTextView = itemView.findViewById<TextView>(R.id.withdraw_amount)
        private val dateTextView = itemView.findViewById<TextView>(R.id.withdraw_date)

        fun bind(withdraw: Withdraw) {
            amountTextView.text = withdraw.withdrawAmount.toString()
            dateTextView.text = withdraw.withdrawDate
        }
    }
}
