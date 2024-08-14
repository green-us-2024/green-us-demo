package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R
import kr.ac.kpu.green_us.data.Market
import kr.ac.kpu.green_us.databinding.ItemsMarketBinding

// MarketAdapter(marketList:ArrayList<Int>)
class MarketAdapter(private val list:ArrayList<Market>):RecyclerView.Adapter<MarketAdapter.MarketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder { // 레이아웃 붙이기
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_market,parent,false)
        val binding = ItemsMarketBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        return MarketViewHolder(view)
        return MarketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) { // 데이터 붙이기
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MarketViewHolder(val binding: ItemsMarketBinding ) : RecyclerView.ViewHolder(binding.root){ // 데이터 붙일 뷰 가져오기
        fun bind(item:Market){
            binding.marketName.text = item.name
            binding.timeClose.text = item.location
            binding.timeOpen.text = item.closed
        }
    }
}