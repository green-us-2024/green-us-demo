package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class CertifiedImgAdapter: RecyclerView.Adapter<CertifiedImgAdapter.CertifiedImgViewHolder>(){

    // 카트 클릭 위한 인터페이스 지정
    interface OnItemClickListener {
        fun onItemClick(){}
    }
    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CertifiedImgViewHolder {
        // view 생성
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_certified_img, parent, false)

        return CertifiedImgViewHolder(view)
    }

    override fun onBindViewHolder(holder: CertifiedImgViewHolder, position: Int) {

        holder.certifiedImg.setImageResource(R.drawable.card_test_img)

    }

    override fun getItemCount(): Int {
        // datalist.count() -> 데이터에 있는 만큼
        return 20
    }

    inner class CertifiedImgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var certifiedImg: ImageView = itemView.findViewById(R.id.imageView4)

        init{
            itemView.setOnClickListener{ itemClickListener?.onItemClick() }
        }

    }



}