package ua.amv0107.recyclerview2mvvm

import android.app.Application
import ua.amv0107.recyclerview2mvvm.model.UsersService

class App: Application() {
    val usersService = UsersService()
}