package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import kr.ac.kpu.green_us.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    private lateinit var join_binding : ActivityJoinBinding
    private val join_manager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        join_binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(join_binding.root)

        showInit()


    }
    private fun showInit(){
        val transaction = join_manager.beginTransaction()
            .add(R.id.join_container,Join1Fragment())
        transaction.commit()
    }
}
