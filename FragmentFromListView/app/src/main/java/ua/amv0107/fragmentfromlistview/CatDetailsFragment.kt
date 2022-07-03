package ua.amv0107.fragmentfromlistview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ua.amv0107.fragmentfromlistview.databinding.FragmentCatDetailsBinding
import ua.amv0107.fragmentfromlistview.model.Cat

class CatDetailsFragment: Fragment() {

    private lateinit var binding : FragmentCatDetailsBinding
    val cat: Cat
        get() = requireArguments().getSerializable(ARG_CAT) as Cat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatDetailsBinding.inflate(inflater, container, false)
        binding.nameTextView.text = cat.name
        binding.descriptionDetailTextView.text = cat.description
        return binding.root
    }

    companion object{
        private const val ARG_CAT = "ARG_CAT"

        fun newInstance(cat: Cat): CatDetailsFragment{
            val fragment = CatDetailsFragment()
            fragment.arguments = bundleOf(ARG_CAT to cat)
            return fragment
        }
    }
}