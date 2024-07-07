package kr.ac.kpu.green_us

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyReviewAdapter: RecyclerView.Adapter<MyReviewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var title: TextView = itemView.findViewById(R.id.title)
        public var review: TextView = itemView.findViewById(R.id.review)
        public var date: TextView = itemView.findViewById(R.id.date)
        public var delete: Button = itemView.findViewById(R.id.delete)
    }
    // 1. Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyReviewAdapter.MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_my_review_cardview, parent, false)

        return MyViewHolder(cardView)
    }

    // 2. Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText("천연세제 사용하기")
        holder.review.setText("너무 좋은 그리닝이었어요!! 다음에 또 참여하고 싶어요")
        holder.date.setText("2024.5.7")
    }

    // 3. Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 10
    }
}