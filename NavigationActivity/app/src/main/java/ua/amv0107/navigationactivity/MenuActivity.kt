package ua.amv0107.navigationactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ua.amv0107.navigationactivity.databinding.ActivityMenuBinding
import ua.amv0107.navigationactivity.model.Options

class MenuActivity : BaseActivity() {
    private lateinit var binding: ActivityMenuBinding
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.openBoxButton.setOnClickListener { onOpenBoxPress() }
        binding.optionsButton.setOnClickListener { onOptionsPress() }
        binding.aboutButton.setOnClickListener { onAboutPress() }
        binding.exitButton.setOnClickListener { onExitPress() }

        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPTION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            options = data?.getParcelableExtra(OptionsActivity.EXTRA_OPTIONS)
                ?: throw IllegalArgumentException("Can't get the update data from options activity")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun onOpenBoxPress() {
        val intent = Intent(this, BoxSelectionActivity::class.java)
        intent.putExtra(BoxSelectionActivity.EXTRA_OPTIONS, options)
        startActivity(intent)
    }

    private fun onOptionsPress() {
        val intent = Intent(this, OptionsActivity::class.java)
        intent.putExtra(OptionsActivity.EXTRA_OPTIONS, options)
        startActivityForResult(intent, OPTION_REQUEST_CODE)
    }

    private fun onAboutPress() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    private fun onExitPress() {
        finish()
    }

    companion object {
        @JvmStatic
        private val KEY_OPTIONS = "OPTIONS"
        @JvmStatic
        private val OPTION_REQUEST_CODE = 1
    }

}