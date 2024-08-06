package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R
import kr.ac.kpu.green_us.common.dto.Greening

class MyGreenOpenAdapter() :
    RecyclerView.Adapter<MyGreenOpenAdapter.MyGreenOpenViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick() {}
    }

    var itemClickListener: MyGreenOpenAdapter.OnItemClickListener? = null
    private var greeningList: List<Greening> = emptyList()



    inner class MyGreenOpenViewHolder(view: View): RecyclerView.ViewHolder(view){
        var img: ImageView = view.findViewById(R.id.greening_img) // 대표이미지
        var title: TextView = view.findViewById(R.id.greeng_title) // 그리닝명
        var deadLine : TextView = view.findViewById(R.id.tv_time) // 마감시간
        var deadLineLayout : LinearLayout = view.findViewById(R.id.deadline_layout) // 마감시간 표시 영역
        var term : TextView = view.findViewById(R.id.tag_term)// 진행기간
        var freq : TextView = view.findViewById(R.id.tag_freq)// 인증빈도
        var method : TextView = view.findViewById(R.id.tag_certifi)// 인증수단
        var type : TextView = view.findViewById(R.id.type) //그리닝 유형
        init{
            view.setOnClickListener{ itemClickListener?.onItemClick() }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyGreenOpenAdapter.MyGreenOpenViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_greening, parent, false)

        return MyGreenOpenViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: MyGreenOpenViewHolder, position: Int) {
        val greening = greeningList[position]
        holder.img.setImageResource(R.drawable.card_test_img)
        holder.title.text = greening.gName
    }

    override fun getItemCount(): Int {
        return greeningList.size
    }

    fun updateData(newList: List<Greening>) {
        greeningList = newList
        notifyDataSetChanged()
    }
}