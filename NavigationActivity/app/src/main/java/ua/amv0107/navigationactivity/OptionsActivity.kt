package ua.amv0107.navigationactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import ua.amv0107.navigationactivity.databinding.ActivityOptionsBinding
import ua.amv0107.navigationactivity.model.Options

class OptionsActivity : BaseActivity() {
    private lateinit var binding: ActivityOptionsBinding
    private lateinit var options: Options

    private lateinit var boxCountItems: List<BoxCountItem>
    private lateinit var adapter: ArrayAdapter<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptionsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        options = savedInstanceState?.getParcelable<Options>(KEY_OPTIONS) ?:
            intent.getParcelableExtra(EXTRA_OPTIONS) ?:
            throw IllegalArgumentException("You need to specify EXTRA_OPTIONS argument to launch this activity")

        setupSpinner()
        updateUi()

        binding.cancelButton.setOnClickListener { onCancelPress() }
        binding.confirmButton.setOnClickListener { onConfirmPress() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun setupSpinner() {
        boxCountItems = (1..6).map { BoxCountItem(it, resources.getQuantityString(R.plurals.boxes, it, it)) }
        adapter = ArrayAdapter(
            this,
            R.layout.item_spinner,
            boxCountItems
        )
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        binding.boxCountSpinner.adapter = adapter
        binding.boxCountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val count = boxCountItems[p2].count
                options = options.copy(boxCount = count)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    private fun updateUi() {
        binding.enableTimerCheckBox.isChecked = options.isTimerEnabled

        val currentIndex = boxCountItems.indexOfFirst { it.count == options.boxCount }
        binding.boxCountSpinner.setSelection(currentIndex)
    }

    private fun onConfirmPress() {
        options = options.copy(isTimerEnabled = binding.enableTimerCheckBox.isChecked)
        val intent = Intent()
        intent.putExtra(EXTRA_OPTIONS, options)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun onCancelPress() {
        finish()
    }

    companion object {
        @JvmStatic
        val EXTRA_OPTIONS = "EXTRA_OPTIONS"
        @JvmStatic
        val KEY_OPTIONS = "KEY_OPTIONS"
    }

    class BoxCountItem(
        val count: Int,
        private val optionTitle: String
    ) {
        override fun toString(): String {
            return optionTitle
        }
    }
}