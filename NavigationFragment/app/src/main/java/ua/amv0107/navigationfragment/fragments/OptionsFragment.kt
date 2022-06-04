package ua.amv0107.navigationfragment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import ua.amv0107.navigationfragment.R
import ua.amv0107.navigationfragment.databinding.FragmentOptionsBinding
import ua.amv0107.navigationfragment.fragments.contract.CustomAction
import ua.amv0107.navigationfragment.fragments.contract.HasCustomAction
import ua.amv0107.navigationfragment.fragments.contract.HasCustomTitle
import ua.amv0107.navigationfragment.fragments.contract.navigator
import ua.amv0107.navigationfragment.model.Options

class OptionsFragment : Fragment(), HasCustomTitle, HasCustomAction {
    private lateinit var binding: FragmentOptionsBinding
    private lateinit var options: Options

    private lateinit var boxCountItems: List<BoxCountItem>
    private lateinit var adapter: ArrayAdapter<BoxCountItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable<Options>(KEY_OPTIONS)
            ?: arguments?.getParcelable(ARG_OPTIONS)
                    ?: throw IllegalArgumentException("You need to specify options to launch this fragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionsBinding.inflate(inflater, container, false)

        setupSpinner()
        setupCheckBox()
        updateUi()

        binding.confirmButton.setOnClickListener { onConfirmPressed() }
        binding.cancelButton.setOnClickListener { onCancelPress() }

        return binding.root
    }

    private fun setupSpinner() {
        boxCountItems = (1..6).map { BoxCountItem(it, resources.getQuantityString(R.plurals.boxes, it, it)) }
        adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner,
            boxCountItems
        )
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        binding.boxCountSpinner.adapter = adapter
        binding.boxCountSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val count = boxCountItems[p2].count
                    options = options.copy(boxCount = count)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }

//    private fun setupSpinner() {
//        boxCountItems =
//            (1..6).map { BoxCountItem(it, resources.getQuantityString(R.plurals.boxes, it, it)) }
//        adapter = ArrayAdapter(
//            this,
//            R.layout.item_spinner,
//            boxCountItems
//        )
//        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
//
//        binding.boxCountSpinner.adapter = adapter
//        binding.boxCountSpinner.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                    val count = boxCountItems[p2].count
//                    options = options.copy(boxCount = count)
//                }
//
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//                }
//            }
//    }

    private fun setupCheckBox() {
        binding.enableTimerCheckBox.setOnClickListener {
            options = options.copy(isTimerEnabled = binding.enableTimerCheckBox.isChecked)
        }
    }

    private fun updateUi() {
        binding.enableTimerCheckBox.isChecked = options.isTimerEnabled

        val currentIndex = boxCountItems.indexOfFirst { it.count == options.boxCount }
        binding.boxCountSpinner.setSelection(currentIndex)
    }

    private fun onCancelPress() {
        navigator().goBack()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    override fun getCustomAction(): CustomAction {
        return CustomAction(
            iconRes = R.drawable.ic_done,
            textRes = R.string.done,
            onCustomAction = Runnable {
                onConfirmPressed()
            }
        )
    }

    private fun onConfirmPressed() {
        navigator().publishResult(options)
        navigator().goBack()
    }

    override fun getTitleRes(): Int = R.string.options

    companion object {
        @JvmStatic
        private val ARG_OPTIONS = "ARG_OPTIONS"

        @JvmStatic
        private val KEY_OPTIONS = "KEY_OPTIONS"

        @JvmStatic
        fun newInstance(options: Options): OptionsFragment {
            val args = Bundle()
            args.putParcelable(ARG_OPTIONS, options)
            val fragment = OptionsFragment()
            fragment.arguments = args
            return fragment
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
}