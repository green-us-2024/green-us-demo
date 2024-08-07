package kr.ac.kpu.green_us.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.ac.kpu.green_us.R
import kr.ac.kpu.green_us.SubActivity

class HeroAdapter(bannerList:MutableList<String>, private val mContext: Context): RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() { //이미지 리스트 가져오는 어댑터
    val itemList = bannerList // 이미지 배열 리스트가 될 것

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val heroImgView = LayoutInflater.from(parent.context).inflate(R.layout.hero_item,parent,false)
        // return HeroViewHolder(heroImgView)
        return HeroViewHolder(heroImgView).apply {
            itemView.setOnClickListener {
                val curPosition = absoluteAdapterPosition%3
                if(curPosition == 0){
                    val intent = Intent(mContext, SubActivity::class.java)
                    intent.putExtra("10","hero_detail")
                    intent.putExtra("10_num", curPosition)
                    mContext.startActivity(intent)
                }
                else if(curPosition == 1){
                    val intent = Intent(mContext, SubActivity::class.java)
                    intent.putExtra("10","hero_detail")
                    intent.putExtra("10_num", curPosition)
                    mContext.startActivity(intent)
                }
                else{
                    val intent = Intent(mContext, SubActivity::class.java)
                    intent.putExtra("10","hero_detail")
                    intent.putExtra("10_num", curPosition)
                    mContext.startActivity(intent)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
//        val imgSize = itemList.size
        Glide.with(holder.itemView.context).load(itemList[position]).into(holder.hero_imgs)
    }

    override fun getItemCount(): Int {
        return itemList.size // 무한스크롤처럼 좌우로 스크롤 가능하도록 많은 값을 반환하게 함
    }

    inner class HeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var hero_imgs : ImageView = itemView.findViewById(R.id.hero_imgs)
    }
}