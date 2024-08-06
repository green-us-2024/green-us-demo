package kr.ac.kpu.green_us.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R
import kr.ac.kpu.green_us.common.dto.Greening

class HomeDoMoreAdapter(private var greeningList: List<Greening> = emptyList()) :
    RecyclerView.Adapter<HomeDoMoreAdapter.HomeDoViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(status:String){}
    }

    var itemClickListener: OnItemClickListener? = null

    inner class HomeDoViewHolder(view: View): RecyclerView.ViewHolder(view){
        var img: ImageView = view.findViewById(R.id.greening_img) // 대표이미지
        var title: TextView = view.findViewById(R.id.greeng_title) // 그리닝명
        var deadLine : TextView = view.findViewById(R.id.tv_time) // 마감시간
        var deadLineLayout : LinearLayout = view.findViewById(R.id.deadline_layout) // 마감시간 표시 영역
        var term : TextView = view.findViewById(R.id.tag_term)// 진행기간
        var freq : TextView = view.findViewById(R.id.tag_freq)// 인증빈도
        var method : TextView = view.findViewById(R.id.tag_certifi)// 인증수단
        var type : TextView = view.findViewById(R.id.type) //그리닝 유형
        init{
            view.setOnClickListener{ itemClickListener?.onItemClick("in") }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): HomeDoMoreAdapter.HomeDoViewHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_greening, parent, false)

        return HomeDoViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: HomeDoViewHolder, position: Int) {
        val greening = greeningList[position]
        holder.img.setImageResource(R.drawable.card_test_img)
        holder.title.text = greening.gName
    }

    override fun getItemCount(): Int {
//            Log.d("HomeDoadapter", "greeningSize : ${greeningList.size}")
        return greeningList.size
    }

    fun updateData(newGreeningList: List<Greening>){
        greeningList = newGreeningList
        notifyDataSetChanged()
    }

}