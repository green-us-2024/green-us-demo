package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class CertifyGreeningAdapter(): RecyclerView.Adapter<CertifyGreeningAdapter.CertifyGreeningViewHolder>() {
    class CertifyGreeningViewHolder(view : View) : RecyclerView.ViewHolder(view){
        public var greeningImg: ImageView = view.findViewById(R.id.img_greening)
        public var greeningTitle: TextView = view.findViewById(R.id.greening_title)
        public var greeningTerm: TextView = view.findViewById(R.id.tag_term)
        public var greeningFreq: TextView = view.findViewById(R.id.tag_freq)
        public var greeningCertiMethod: TextView = view.findViewById(R.id.tag_certifi)
        public var startDate: TextView = view.findViewById(R.id.tv_start_date)
        public var particiFee: TextView = view.findViewById(R.id.tv_participate_fee)
        public var certifiImg1: ImageView = view.findViewById(R.id.cerfi_img_1)
        public var certifiImg2: ImageView = view.findViewById(R.id.cerfi_img_2)
        public var certifiImg3: ImageView = view.findViewById(R.id.cerfi_img_3)
        public var stamps : RecyclerView = view.findViewById(R.id.stamps_layout)
    }
    override fun onBindViewHolder(holder: CertifyGreeningAdapter.CertifyGreeningViewHolder, position: Int) {
        holder.greeningImg.setImageResource(R.drawable.card_test_img)
        holder.greeningTitle.setText("대중교통 이용하기")
        holder.greeningTerm.setText("주4회")
        holder.greeningFreq.setText("2주")
        holder.greeningCertiMethod.setText("카메라")
        holder.startDate.setText("7월 9일부터 시작")
        holder.particiFee.setText("2,000")
        holder.certifiImg1.setImageResource(R.drawable.card_test_img)
        holder.certifiImg2.setImageResource(R.drawable.card_test_img)
        holder.certifiImg3.setImageResource(R.drawable.card_test_img)
        holder.stamps.apply {
            layoutManager = GridLayoutManager(context,3)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertifyGreeningAdapter.CertifyGreeningViewHolder {
        val greeingDetailView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_greening_detail, parent, false)

        return CertifyGreeningAdapter.CertifyGreeningViewHolder(greeingDetailView)

    }

    override fun getItemCount(): Int {
        return 1
    }
}