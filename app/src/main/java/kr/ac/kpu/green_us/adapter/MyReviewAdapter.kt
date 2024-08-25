package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.DeleteCheckActivity
import kr.ac.kpu.green_us.MyReviewActivity
import kr.ac.kpu.green_us.R
import kr.ac.kpu.green_us.common.dto.Review

class MyReviewAdapter() :

    RecyclerView.Adapter<MyReviewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var review: TextView = itemView.findViewById(R.id.review)
        var date: TextView = itemView.findViewById(R.id.date)
        var delete: Button = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyReviewAdapter.MyViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_my_review, parent, false)

        return MyViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.title.text = "굿"
        holder.review.text = "좋다"
        holder.date.text = "2022.2.2"
        holder.delete.setOnClickListener {
            val dlg = DeleteCheckActivity(holder.itemView.context as MyReviewActivity)
            dlg.setOnDeleteClickedListener { content ->
                if (content == 3) {
                }
            }
            dlg.show()
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
}