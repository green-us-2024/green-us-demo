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
        //onItemClick(position: Int)
        // 매개변수에다 그리닝의 상태(진행중인지 아닌지를 포함하여 상세페이지 버튼 값이 달라져야 함)
//         fun onItemClick(position:Int,status:String ) {}
        fun onItemClick() {}
    }

    var itemClickListener: MyGreenEndAdapter.OnItemClickListener? = null

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

    // 1. Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyGreenEndAdapter.GreenCardHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_greening, parent, false)

        return GreenCardHolder(cardView)
    }

    // 2. Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: GreenCardHolder, position: Int) {
        holder.itemimage.setImageResource(R.drawable.card_test_img)
        holder.itemtitle.setText("테스트 그리닝")
    }

    // 3. Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 4
    }
}