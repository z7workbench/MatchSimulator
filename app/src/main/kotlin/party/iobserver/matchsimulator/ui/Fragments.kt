package party.iobserver.matchsimulator.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_me.view.*
import party.iobserver.matchsimulator.R
import party.iobserver.matchsimulator.app

class TeamsFragment : Fragment() {
    private lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_teams, container, false)
        return rootView
    }

}

class MatchesFragment : Fragment() {
    private lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_matches, container, false)

        return rootView
    }

}

class MeFragment : Fragment() {
    private lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_me, container, false)
        rootView.hideAndShow.setOnClickListener {
            hideOrNot(true)
        }
        hideOrNot(false)
        fragmentManager!!.beginTransaction()
                .replace(R.id.content, SettingsFragment())
                .commit()
        return rootView
    }


    private fun hideOrNot(edit: Boolean) {
        val hide = app.prefs.getBoolean("hide", true)
        if ((!hide && edit) || (hide && !edit)) {
            rootView.hideAndShow.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_show))
            rootView.cash.setText(getString(R.string.me_cash) + "****")
            if (edit) {
                val editor = app.prefs.edit()
                editor.putBoolean("hide", true)
                editor.apply()
            }

        } else {
            rootView.hideAndShow.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_hide))
            val cashInt: Int = app.prefs.getInt("cash", 0)
            rootView.cash.setText(getString(R.string.me_cash) + cashInt)
            if (edit) {
                val editor = app.prefs.edit()
                editor.putBoolean("hide", false)
                editor.apply()
            }
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.prefs)
        }
    }

}