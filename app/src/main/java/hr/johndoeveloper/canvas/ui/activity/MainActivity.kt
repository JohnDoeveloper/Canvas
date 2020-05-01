package hr.johndoeveloper.canvas.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import hr.johndoeveloper.canvas.R
import hr.johndoeveloper.canvas.ui.fragment.PieChartFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        beginFragmentTransaction(PieChartFragment())
    }

    fun beginFragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            addToBackStack(fragment.tag)
        }.replace(R.id.frameLayout, fragment)
            .commitAllowingStateLoss()
    }
}
