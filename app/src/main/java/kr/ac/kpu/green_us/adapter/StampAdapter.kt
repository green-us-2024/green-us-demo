package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kr.ac.kpu.green_us.R

class StampAdapter():RecyclerView.Adapter<StampAdapter.StampHolder>() {
    override fun getItemCount(): Int {
        // return 스탬프 인증횟수만큼
        return 5
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StampHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_stamps,parent,false)

        return StampHolder(view)
    }
    inner class StampHolder(view: View): RecyclerView.ViewHolder(view){
        var stampDate : TextView = view.findViewById(R.id.stamp_date)
    }
    override fun onBindViewHolder(holder: StampAdapter.StampHolder, position: Int) {
        holder.stampDate.text = "7.31"
    }
}