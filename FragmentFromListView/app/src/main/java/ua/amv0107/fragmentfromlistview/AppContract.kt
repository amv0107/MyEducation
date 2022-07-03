package ua.amv0107.fragmentfromlistview

import androidx.fragment.app.Fragment
import ua.amv0107.fragmentfromlistview.model.Cat
import ua.amv0107.fragmentfromlistview.model.CatsService

fun Fragment.contract(): AppContract = requireActivity() as AppContract

interface AppContract {
    val catsService: CatsService

    fun launchCatDetailScreen(cat: Cat)
}