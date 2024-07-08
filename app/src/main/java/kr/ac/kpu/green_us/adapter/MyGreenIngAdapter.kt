package kr.ac.kpu.green_us.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import kr.ac.kpu.green_us.R

class MyGreenIngAdapter: RecyclerView.Adapter<MyGreenIngAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var title: TextView = itemView.findViewById(R.id.greening_title_pro)
        public var percentage: TextView = itemView.findViewById(R.id.greening_percentage)
        public var progressbar: LinearProgressIndicator = itemView.findViewById(R.id.linearProgressIndicator)
    }
    // 1. Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_green_degree, parent, false)

        return MyViewHolder(cardView)
    }

    // 2. Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText("옥수수 수세미 사용하기")
        holder.percentage.setText("70%")
        holder.progressbar.setProgress(70)
    }

    // 3. Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 5
    }
}