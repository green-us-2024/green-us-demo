package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class GreeningDetailAdapter() : RecyclerView.Adapter<GreeningDetailAdapter.GreeningDetailViewHolder>(){
    class GreeningDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        public var itemimage: ImageView = itemView.findViewById(R.id.img_greening)
        public var itemtitle: TextView = itemView.findViewById(R.id.greening_title)
        public var itemTerm: TextView = itemView.findViewById(R.id.tag_term)
        public var itemFreq: TextView = itemView.findViewById(R.id.tag_freq)
        public var itemCertifi: TextView = itemView.findViewById(R.id.tag_certifi)
        public var itemStartDate: TextView = itemView.findViewById(R.id.tv_start_date)
        public var itemParticiFee: TextView = itemView.findViewById(R.id.tv_participate_fee)
        public var itemHowTo: TextView = itemView.findViewById(R.id.tv_howto)
        public var officialImg: ImageView = itemView.findViewById(R.id.img_when_official)
    }

    override fun onBindViewHolder(holder: GreeningDetailViewHolder, position: Int) {
        holder.itemimage.setImageResource(R.drawable.card_test_img)
        holder.itemtitle.setText("대중교통 이용하기")
        holder.itemFreq.setText("주4회")
        holder.itemTerm.setText("2주")
        holder.itemCertifi.setText("카메라")
        holder.itemStartDate.setText("7월 9일부터 시작")
        holder.itemParticiFee.setText("2,000")
        holder.itemHowTo.setText("이렇게저렇게해요이렇게저렇게해요이렇게저렇게해요이렇게저렇게해요이렇게저렇게해요")
        holder.officialImg.setImageResource(R.drawable.test_greening_detail_official)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GreeningDetailViewHolder {
        val greeingDetailView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_greening_detail, parent, false)

        return GreeningDetailViewHolder(greeingDetailView)

    }

    override fun getItemCount(): Int {
        return 1
    }

}