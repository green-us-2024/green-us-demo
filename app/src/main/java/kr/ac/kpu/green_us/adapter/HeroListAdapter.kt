package kr.ac.kpu.green_us.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.storage.FirebaseStorage
import kr.ac.kpu.green_us.R

class HeroListAdapter(): RecyclerView.Adapter<HeroListAdapter.HeroCardHolder>() {
    class HeroCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemimage: ImageView = itemView.findViewById(R.id.banner)
    }

    // 1. Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): HeroListAdapter.HeroCardHolder {
        // create a new view
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_hero, parent, false)

        return HeroCardHolder(cardView)
    }

    // 2. Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: HeroCardHolder, position: Int) {
        val storage = FirebaseStorage.getInstance()
        holder.itemimage.setImageResource(R.drawable.hero_img_1)
    }

    // 3. Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 4
    }

}