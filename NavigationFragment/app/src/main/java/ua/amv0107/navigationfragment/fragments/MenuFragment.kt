package ua.amv0107.navigationfragment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.amv0107.navigationfragment.databinding.FragmentMenuBinding
import ua.amv0107.navigationfragment.fragments.contract.navigator
import ua.amv0107.navigationfragment.model.Options

class MenuFragment : Fragment() {
    private lateinit var options: Options

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        options = savedInstanceState?.getParcelable(KEY_OPTIONS) ?: Options.DEFAULT
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMenuBinding.inflate(inflater, container, false)

        navigator().listenResult(Options::class.java, viewLifecycleOwner){
            this.options = it
        }

        binding.openBoxButton.setOnClickListener { onOpenBoxPres() }
        binding.optionsButton.setOnClickListener { onOptionPress() }
        binding.aboutButton.setOnClickListener { onAboutPress() }
        binding.exitButton.setOnClickListener { onExitPress() }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY_OPTIONS, options)
    }

    private fun onOpenBoxPres() {
        navigator().showBoxSelectionScreen(options)
    }

    private fun onOptionPress() {
        navigator().showOptionsScreen(options)
    }

    private fun onAboutPress() {
        navigator().showAboutScreen()
    }

    private fun onExitPress() {
        navigator().goBack()
    }

    companion object{
        @JvmStatic private val KEY_OPTIONS = "OPTIONS"
    }
}