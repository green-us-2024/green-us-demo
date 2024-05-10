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

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val btn_next : Button = findViewById(R.id.btn_next)
        btn_next.setOnClickListener {
            val intent = Intent(this,Join2Activity::class.java)
            startActivity(intent)
        }
        val btn_prev : ImageButton = findViewById(R.id.btn_prev)
        btn_prev.setOnClickListener {
            val intent_prev = Intent(this,LoginActivity::class.java)
            startActivity(intent_prev)
        }

        val tv_idCheck : TextView = findViewById(R.id.tv_idCheck)
/*val et_id : EditText = findViewById(R.id.et_id)*/
        /*val userId : String = et_id.text.toString()*/
        val btn_check : Button = findViewById(R.id.btn_check)
        btn_check.setOnClickListener {
            tv_idCheck.setText(R.string.not_allowed)
            tv_idCheck.setTextColor("#000099".toColorInt())
            tv_idCheck.visibility = View.VISIBLE
        }
    }
}
