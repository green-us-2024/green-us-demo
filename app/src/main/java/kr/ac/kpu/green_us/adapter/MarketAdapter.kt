package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R
// MarketAdapter(marketList:ArrayList<Int>)
class MarketAdapter():RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {
//    val itemList = marketList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder { // 레이아웃 붙이기
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_market,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) { // 데이터 붙이기
//        holder.market_name.text = itemList[position].어떤거
        holder.market_name.text = "친환경가게명"
        holder.market_open.text = "08:00"
        holder.market_close.text = "22:00"
    }

    override fun getItemCount(): Int {
//        return itemList.count()
        // 임의로 5개를 반환하도록 하였습니다
        return 20
    }

    inner class MarketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){ // 데이터 붙일 뷰 가져오기
        val market_name = itemView.findViewById<TextView>(R.id.market_name)
        val market_open = itemView.findViewById<TextView>(R.id.time_open)
        val market_close = itemView.findViewById<TextView>(R.id.time_close)
    }
}