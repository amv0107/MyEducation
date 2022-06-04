package ua.amv0107.navigationfragment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.amv0107.navigationfragment.R
import ua.amv0107.navigationfragment.databinding.FragmentAboutBinding
import ua.amv0107.navigationfragment.fragments.contract.HasCustomTitle
import ua.amv0107.navigationfragment.fragments.contract.navigator

class AboutFragment : Fragment(), HasCustomTitle {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentAboutBinding.inflate(inflater, container, false).apply{
        versionNameTextView.text = "VersionName"
        versionCodeTextView.text = "VersionCode"
        okButton.setOnClickListener { onOkPress() }
        }.root

    private fun onOkPress() {
        navigator().goBack()
    }

    override fun getTitleRes(): Int = R.string.about
}