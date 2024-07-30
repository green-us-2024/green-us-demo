package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class TabNewAdapter(): RecyclerView.Adapter<TabNewAdapter.TabNewViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(status:String){}
    }
    var itemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return 20
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabNewViewHolder {
        // view 생성
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_greening, parent, false)

        return TabNewViewHolder(view)
    }

    override fun onBindViewHolder(holder: TabNewViewHolder, position: Int) {
        holder.itemimage.setImageResource(R.drawable.card_test_img)
        holder.itemtitle.setText("테스트 그리닝")
    }

    inner class TabNewViewHolder(view: View): RecyclerView.ViewHolder(view){
        val cardView: ConstraintLayout = view.findViewById(R.id.card_container)
        var itemimage: ImageView = view.findViewById(R.id.greening_img)
        var itemtitle: TextView = view.findViewById(R.id.greeng_title)
        init{
            view.setOnClickListener{ itemClickListener?.onItemClick("in") }
        }
    }

}