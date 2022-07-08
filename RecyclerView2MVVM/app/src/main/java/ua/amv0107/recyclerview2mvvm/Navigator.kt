package ua.amv0107.recyclerview2mvvm

import ua.amv0107.recyclerview2mvvm.model.User

interface Navigator {
    fun showDetails(user: User)

    fun goBack()

    fun toast(messageRes: Int)
}