package party.iobserver.matchsimulator.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import party.iobserver.matchsimulator.R

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
        return rootView
    }

}
