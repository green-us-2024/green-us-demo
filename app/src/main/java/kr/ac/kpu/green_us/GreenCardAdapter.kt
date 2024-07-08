package kr.ac.kpu.green_us

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GreenCardAdapter() :
    RecyclerView.Adapter<GreenCardAdapter.GreenCardHolder>(){
        class GreenCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            public var itemimage: ImageView = itemView.findViewById(R.id.greening_img)
            public var itemtitle: TextView = itemView.findViewById(R.id.greeng_title)
        }

        // 1. Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): GreenCardAdapter.GreenCardHolder {
            // create a new view
            val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.cardview_greening, parent, false)

            return GreenCardHolder(cardView)
        }

        // 2. Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: GreenCardHolder, position: Int) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.itemimage.setImageResource(R.drawable.card_test_img)
            holder.itemtitle.setText("테스트 그리닝")
        }

        // 3. Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount(): Int {
            return 20
        }
}