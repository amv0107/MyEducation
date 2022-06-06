package ua.amv0107.dialogfragmens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ua.amv0107.dialogfragmens.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.level1Button.setOnClickListener {
            startActivity(Intent(this, DialogsLevel1Activity::class.java))
        }

        binding.level2Button.setOnClickListener {
            startActivity(Intent(this, DialogsLevel2Activity::class.java))
        }

        binding.exitButton.setOnClickListener {
            finish()
        }
    }
}