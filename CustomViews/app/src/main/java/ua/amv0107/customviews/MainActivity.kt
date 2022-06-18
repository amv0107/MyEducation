package ua.amv0107.customviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import ua.amv0107.customviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.bottomButtons.setListener {
            if (it == BottomButtonAction.POSITIVE) {
                    binding.bottomButtons.setPositiveButtonText("Update OK")

            } else if (it == BottomButtonAction.NEGATIVE) {
                binding.bottomButtons.setNegativeButtonText("Update Cancel")
            }
        }
    }
}