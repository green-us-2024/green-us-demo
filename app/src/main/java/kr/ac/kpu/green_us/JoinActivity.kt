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
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kr.ac.kpu.green_us.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    private lateinit var binding : ActivityJoinBinding
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //첫번째로 보일 화면
        showInit()
    }

    private fun showInit(){
        val transaction = manager.beginTransaction()
            .replace(R.id.join_container,Join1Fragment())
        transaction.commit()
    }
    fun changeFrag(index: Int){
        when(index){
            2 -> {
                manager.beginTransaction().replace(R.id.join_container,Join2Fragment()).addToBackStack(null).commit()
            }
            3 -> {
                manager.beginTransaction().replace(R.id.join_container,Join3Fragment()).addToBackStack(null).commit()

            }
            4-> {
                manager.beginTransaction().replace(R.id.join_container,JoinAddressFragment()).addToBackStack(null).commit()
            }
            5 -> {
                manager.beginTransaction().replace(R.id.join_container,JoinCompltFragment()).addToBackStack(null).commit()
            }
            6 -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
