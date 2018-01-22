package party.iobserver.matchsimulator.ui

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import party.iobserver.matchsimulator.Constants
import party.iobserver.matchsimulator.R
import party.iobserver.matchsimulator.app
import party.iobserver.matchsimulator.util.AlertEnum
import party.iobserver.matchsimulator.util.showSimpleDialog

class MainActivity : AppCompatActivity() {
    var menuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewPager.adapter = FragmentsAdapter(supportFragmentManager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {
                menuItem?.isChecked = false
                if (menuItem == null) {
                    navigation.menu.getItem(Constants.TEAMS).isChecked = false
                }
                menuItem = navigation.menu.getItem(position)
                menuItem!!.isChecked = true
            }
        })

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_team -> viewPager.currentItem = Constants.TEAMS
                R.id.navigation_match -> viewPager.currentItem = Constants.MATCHES
                R.id.navigation_me -> viewPager.currentItem = Constants.ME
            }
            return@setOnNavigationItemSelectedListener true
        }

        if (app.prefs.getInt("cash", 0) <= 0) {
            showSimpleDialog(this, AlertEnum.HINT, R.string.if_no_cash)
            app.prefs.edit().putInt("cash", 5000).apply()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                true
            }
            R.id.action_add -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
