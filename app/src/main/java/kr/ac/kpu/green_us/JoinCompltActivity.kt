package kr.ac.kpu.green_us

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt

class JoinCompltActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_complt)

        val btn_go_to_login : Button = findViewById(R.id.btn_go_to_login)
        btn_go_to_login.setOnClickListener {
            val intent_go_login = Intent(this, LoginActivity::class.java)
            startActivity(intent_go_login)
        }
    }
}