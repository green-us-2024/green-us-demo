package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class GreeningDetailSubAdapter() : RecyclerView.Adapter<GreeningDetailSubAdapter.GreeningDetailSubViewHolder>(){
    class GreeningDetailSubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var itemimage: ImageView = itemView.findViewById(R.id.img_greening)
        public var itemtitle: TextView = itemView.findViewById(R.id.greening_title)
        public var itemTerm: TextView = itemView.findViewById(R.id.tag_term)
        public var itemFreq: TextView = itemView.findViewById(R.id.tag_freq)
        public var itemCertifi: TextView = itemView.findViewById(R.id.tag_certifi)
        public var itemStartDate: TextView = itemView.findViewById(R.id.tv_start_date)
        public var itemParticiFee: TextView = itemView.findViewById(R.id.tv_participate_fee)
    }

    override fun onBindViewHolder(holder: GreeningDetailSubViewHolder, position: Int) {
        holder.itemimage.setImageResource(R.drawable.card_test_img)
        holder.itemtitle.setText("대중교통 이용하기")
        holder.itemFreq.setText("주4회")
        holder.itemTerm.setText("2주")
        holder.itemCertifi.setText("카메라")
        holder.itemStartDate.setText("7월 9일부터 시작")
        holder.itemParticiFee.setText("2,000")

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreeningDetailSubViewHolder {
        val greeingDetailView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_greening_detail_sub, parent, false)

        return GreeningDetailSubViewHolder(greeingDetailView)

    }

    override fun getItemCount(): Int {
        return 1
    }

}