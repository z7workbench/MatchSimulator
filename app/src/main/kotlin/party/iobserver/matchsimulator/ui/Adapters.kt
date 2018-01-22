package party.iobserver.matchsimulator.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import party.iobserver.matchsimulator.Constants.MATCHES
import party.iobserver.matchsimulator.Constants.ME
import party.iobserver.matchsimulator.Constants.TEAMS


class FragmentsAdapter(private val manager: FragmentManager) : FragmentPagerAdapter(manager) {
    override fun getItem(position: Int): Fragment? {
        when (position) {
            TEAMS -> {
                return TeamsFragment()
            }
            MATCHES -> {
                return MatchesFragment()
            }
            ME -> {
                return MeFragment()
            }
        }
        return null
    }

    override fun getCount() = 3
}