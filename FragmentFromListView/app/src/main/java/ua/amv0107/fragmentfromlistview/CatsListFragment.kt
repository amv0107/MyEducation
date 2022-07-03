package ua.amv0107.fragmentfromlistview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import ua.amv0107.fragmentfromlistview.databinding.FragmentCatsListBinding

class CatsListFragment: Fragment() {

    private lateinit var binding: FragmentCatsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatsListBinding.inflate(inflater, container, false)

        val cats = contract().catsService.cats
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, cats)
        binding.catsListView.adapter = adapter

        binding.catsListView.setOnItemClickListener { _, _, position, _ ->
            val currentCat = adapter.getItem(position)!!
            contract().launchCatDetailScreen(currentCat)
        }
        return binding.root
    }
}