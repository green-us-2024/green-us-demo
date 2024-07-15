package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class HeroAdapter(bannerList:ArrayList<Int>): RecyclerView.Adapter<HeroAdapter.HeroViewHolder>() { //이미지 리스트 가져오는 어댑터
    val itemList = bannerList // 이미지 배열 리스트가 될 것

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val heroImgView = LayoutInflater.from(parent.context).inflate(R.layout.hero_item,parent,false)

        return HeroViewHolder(heroImgView)
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        val imgSize = itemList.size
        holder.hero_imgs.setImageResource(itemList[position%imgSize]) //positon(max int)를 배너 이미지의 개수로 나눈 나머지 값을 사용함
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE // 무한스크롤처럼 좌우로 스크롤 가능하도록 많은 값을 반환하게 함
    }

    inner class HeroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var hero_imgs : ImageView = itemView.findViewById(R.id.hero_imgs)
    }
}