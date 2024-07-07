package kr.ac.kpu.green_us

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NoticeAdapter: RecyclerView.Adapter<NoticeAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var title: TextView = itemView.findViewById(R.id.title)
        public var btnDown: ImageView = itemView.findViewById(R.id.btn_down)
        public var content: TextView = itemView.findViewById(R.id.content)
        public var date: TextView = itemView.findViewById(R.id.date)
        public var constraintLayout2: LinearLayout = itemView.findViewById(R.id.constraintLayout2)
        public var constraintLayout3: LinearLayout = itemView.findViewById(R.id.constraintLayout3)
    }
    // 1. Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NoticeAdapter.MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_notice_cardview, parent, false)

        return MyViewHolder(cardView)
    }

    // 2. Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText("그리닝 개설 제한일 변경 안내")
        holder.btnDown.setOnClickListener{
            if(holder.constraintLayout3.visibility == View.VISIBLE){
                holder.btnDown.setImageResource(R.drawable.btn_down)
                holder.constraintLayout3.visibility = View.GONE
            } else {
                holder.btnDown.setImageResource(R.drawable.btn_up)
                holder.constraintLayout3.visibility = View.VISIBLE
            }
        }
        holder.content.setText("기존: 주 1회 -> 변경: 제한 없음. 변경사항은 익일부터 실시됩니다.")
        holder.date.setText("2024.5.7")
    }

    // 3. Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 10
    }
}