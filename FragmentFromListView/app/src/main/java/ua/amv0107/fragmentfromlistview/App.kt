package ua.amv0107.fragmentfromlistview

import android.app.Application
import ua.amv0107.fragmentfromlistview.model.CatsService

class App: Application() {

    val catService = CatsService()
}