package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Join2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join2)

        val btn_prev : ImageButton = findViewById(R.id.btn_esc)
        btn_prev.setOnClickListener {
            val intent_prev = Intent(this,JoinActivity::class.java)
            startActivity(intent_prev)
        }
        val btn_next : Button = findViewById(R.id.btn_next)
        btn_next.setOnClickListener {
            val intent_next = Intent(this,JoinCompltActivity::class.java)
            startActivity(intent_next)
        }
    }
}
