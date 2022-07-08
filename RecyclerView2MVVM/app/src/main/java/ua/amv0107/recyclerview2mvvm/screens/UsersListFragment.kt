package ua.amv0107.recyclerview2mvvm.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ua.amv0107.recyclerview2mvvm.UserActionListener
import ua.amv0107.recyclerview2mvvm.UsersAdapter
import ua.amv0107.recyclerview2mvvm.databinding.FragmentUsersListBinding
import ua.amv0107.recyclerview2mvvm.model.User
import ua.amv0107.recyclerview2mvvm.tasks.EmptyResult
import ua.amv0107.recyclerview2mvvm.tasks.ErrorResult
import ua.amv0107.recyclerview2mvvm.tasks.PendingResult
import ua.amv0107.recyclerview2mvvm.tasks.SuccessResult

class UsersListFragment: Fragment() {

    private lateinit var binding: FragmentUsersListBinding
    private lateinit var adapter: UsersAdapter

    private val viewModel: UsersListViewModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        adapter = UsersAdapter(viewModel)

        viewModel.users.observe(viewLifecycleOwner, Observer {
            HideAll()
            when(it){
                is SuccessResult -> {
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.users = it.data
                }
                is ErrorResult -> {
                    binding.tryAgainContainer.visibility = View.VISIBLE
                }
                is PendingResult -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is EmptyResult -> {
                    binding.noUsersTextView.visibility = View.VISIBLE
                }
            }
        })

        viewModel.actionShowDetails.observe(viewLifecycleOwner, Observer{
            it.getValue()?.let { user -> navigator().showDetails(user) }
        })
        viewModel.actionShowToast.observe(viewLifecycleOwner, Observer{
           it.getValue()?.let { messageRes -> navigator().toast(messageRes) }
        })

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    private fun HideAll(){
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.tryAgainContainer.visibility = View.GONE
        binding.noUsersTextView.visibility = View.GONE
    }
}