package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class PointAdapter: RecyclerView.Adapter<PointAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var day: TextView = itemView.findViewById(R.id.day)
        public var category: TextView = itemView.findViewById(R.id.category)
        public var greening_title: TextView = itemView.findViewById(R.id.greening_title)
        public var get_point: TextView = itemView.findViewById(R.id.get_point)
        public var total_point: TextView = itemView.findViewById(R.id.total_point)
    }
    // 1. Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_point, parent, false)

        return MyViewHolder(cardView)
    }

    // 2. Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.day.setText("7.12")
        holder.category.setText("획득")
        holder.greening_title.setText("천연비누 사용하기")
        holder.get_point.setText("+ 2,000")
        holder.total_point.setText("잔액 7,000")
    }

    // 3. Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 10
    }
}