package ua.amv0107.navigationfragment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.amv0107.navigationfragment.R
import ua.amv0107.navigationfragment.databinding.FragmentBoxBinding
import ua.amv0107.navigationfragment.fragments.contract.HasCustomTitle
import ua.amv0107.navigationfragment.fragments.contract.navigator

class BoxFragment : Fragment(), HasCustomTitle {
    private lateinit var binding: FragmentBoxBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoxBinding.inflate(layoutInflater, container, false)

        binding.toMainMenuButton.setOnClickListener { onToMainMenuPressed() }

        return binding.root
    }

    private fun onToMainMenuPressed() {
        navigator().goToMenu()
    }

    override fun getTitleRes(): Int  = R.string.box
}