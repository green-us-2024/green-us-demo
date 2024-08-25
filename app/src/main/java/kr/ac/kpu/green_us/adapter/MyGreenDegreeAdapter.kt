package kr.ac.kpu.green_us.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import kr.ac.kpu.green_us.R
import kr.ac.kpu.green_us.common.dto.Greening
import kr.ac.kpu.green_us.common.dto.Participate

class MyGreenDegreeAdapter(private var participateList: List<Participate>, private var greeningList: List<Greening>) :
    RecyclerView.Adapter<MyGreenDegreeAdapter.MyGreenDegreeViewHolder>() {

    inner class MyGreenDegreeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.greening_title_pro) // 그리닝명
        var percentage: TextView = view.findViewById(R.id.greening_percentage) // 그리닝 진행률 %
        var progressbar: LinearProgressIndicator =
            view.findViewById(R.id.linearProgressIndicator) // 그리닝 프로그레스바
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyGreenDegreeViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_green_degree, parent, false)

        return MyGreenDegreeViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: MyGreenDegreeViewHolder, position: Int) {
        val greening = greeningList.getOrNull(position)
        val participate = participateList.getOrNull(position)

        Log.d("MyGreenDegreeAdapter", "Binding position $position: participate = $participate, greening = $greening")

        if (greening != null && participate != null) {
            holder.title.text = greening.gName
            val percentage = participate.pCount?.toDouble()?.div(greening.gNumber ?: 1)?.times(100) ?: 0.0
            holder.percentage.text = "${percentage.toInt()}%"
            holder.progressbar.progress = percentage.toInt()
        }
    }

    override fun getItemCount(): Int {
        return greeningList.size
    }

    fun updateData(newList1: List<Participate>, newList2: List<Greening>) {
        participateList = newList1
        greeningList = newList2
        notifyDataSetChanged()
    }
}