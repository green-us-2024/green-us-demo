package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class ReviewAdapter :
    RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userId: TextView = itemView.findViewById(R.id.userId)
        var reviewRating: RatingBar = itemView.findViewById(R.id.review_rating)
        var review: TextView = itemView.findViewById(R.id.review)
        var date: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReviewAdapter.MyViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_review, parent, false)

        return MyViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.userId.text = "ji"
        holder.review.text = "good"
        holder.date.text = "2022.2.2"
    }

    override fun getItemCount(): Int {
        return 5
    }

}