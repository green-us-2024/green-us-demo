package kr.ac.kpu.green_us.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.GreeningDetailActivity
import kr.ac.kpu.green_us.R

// 데이터 받은 매개변수 datalist로 수정
class GreenCardAdapter() : RecyclerView.Adapter<GreenCardAdapter.GreenCardHolder>(){

    // 카트 클릭 위한 인터페이스 지정
    interface OnItemClickListener {
        //onItemClick(position: Int)
        // 매개변수에다 그리닝의 상태(진행중인지 아닌지를 포함하여 상세페이지 버튼 값이 달라져야 함)
//         fun onItemClick(position:Int,status:String ) {}
        fun onItemClick(status:String){}
    }
    var itemClickListener: OnItemClickListener? = null

    // 1. Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): GreenCardHolder {
        // view 생성
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_greening, parent, false)

        return GreenCardHolder(view)
    }

    override fun onBindViewHolder(holder: GreenCardHolder, position: Int) {
        // datalist에서 포지션에따라 값 붙여야 함
        // holder.itemtitle.text = datalist[position].title

        holder.itemimage.setImageResource(R.drawable.card_test_img)
        holder.itemtitle.setText("테스트 그리닝")

    }

    override fun getItemCount(): Int {
        // datalist.count() -> 데이터에 있는 만큼
        return 20
    }

    inner class GreenCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

         val cardView: ConstraintLayout = itemView.findViewById(R.id.card_container)
         var itemimage: ImageView = itemView.findViewById(R.id.greening_img)
         var itemtitle: TextView = itemView.findViewById(R.id.greeng_title)

        init{
            itemView.setOnClickListener{

                itemClickListener?.onItemClick("notIn")
            }
        }

    }



}