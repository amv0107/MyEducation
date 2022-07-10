package ua.amv0107.recyclerview2mvvm.screens

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ua.amv0107.recyclerview2mvvm.App
import ua.amv0107.recyclerview2mvvm.Navigator

typealias ViewModelCreator = (App) -> ViewModel?

class ViewModelFactory(
    private val app: App,
    private val viewModelCreator: ViewModelCreator = { null }
):ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            UsersListViewModel::class.java -> {
                UsersListViewModel(app.usersService)
            }
            else -> {
                viewModelCreator(app) ?: throw IllegalStateException("Unknown view model class!")
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)

fun Fragment.navigator() = requireActivity() as Navigator

inline fun <reified VM: ViewModel> Fragment.viewModelCreator(noinline creator: ViewModelCreator): Lazy<VM> {
    return viewModels { ViewModelFactory(requireContext().applicationContext as App, creator) }
}
