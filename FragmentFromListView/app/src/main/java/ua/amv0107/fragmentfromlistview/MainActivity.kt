package ua.amv0107.fragmentfromlistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ua.amv0107.fragmentfromlistview.model.Cat
import ua.amv0107.fragmentfromlistview.model.CatsService

class MainActivity : AppCompatActivity(), AppContract {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, CatsListFragment())
                .commit()
        }
    }

    override val catsService: CatsService
        get() = (applicationContext as App).catService

    override fun launchCatDetailScreen(cat: Cat) {
        val fragment = CatDetailsFragment.newInstance(cat)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}