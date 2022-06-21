package ua.amv0107.recyclerview

import android.app.Application
import ua.amv0107.recyclerview.model.UsersService

class App: Application() {
    val usersService = UsersService()
}