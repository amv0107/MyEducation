package ua.amv0107.listviewandspinner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ua.amv0107.listviewandspinner.baseadapter.BasedAdapterActivity
import ua.amv0107.listviewandspinner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.b1.setOnClickListener {
            val intent = Intent(this, SimpleAdapterActivity::class.java)
            startActivity(intent)
        }

        binding.b2.setOnClickListener {
            val intent = Intent(this, ArrayAdapterActivity::class.java)
            startActivity(intent)
        }

        binding.b3.setOnClickListener {
            val intent = Intent(this, BasedAdapterActivity::class.java)
            startActivity(intent)
        }

        binding.b4.setOnClickListener {
            val intent = Intent(this, SpinnerActivity::class.java)
            startActivity(intent)
        }
    }
}