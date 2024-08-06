package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class MyGreenEndAdapter() :
    RecyclerView.Adapter<MyGreenEndAdapter.GreenCardHolder>() {

    interface OnItemClickListener {
        fun onItemClick() {}
    }

    var itemClickListener: OnItemClickListener? = null

    inner class GreenCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: ConstraintLayout = itemView.findViewById(R.id.card_container)
        var itemimage: ImageView = itemView.findViewById(R.id.greening_img)
        var itemtitle: TextView = itemView.findViewById(R.id.greeng_title)
        init{
            itemView.setOnClickListener{

                itemClickListener?.onItemClick()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyGreenEndAdapter.GreenCardHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_greening, parent, false)

        return GreenCardHolder(cardView)
    }

    override fun onBindViewHolder(holder: GreenCardHolder, position: Int) {
        holder.itemimage.setImageResource(R.drawable.card_test_img)
        holder.itemtitle.setText("테스트 그리닝")
    }

    override fun getItemCount(): Int {
        return 4
    }
}