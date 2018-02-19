package party.iobserver.matchsimulator.ui

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_properties.view.*
import kotlinx.android.synthetic.main.item_teams.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivityForResult
import party.iobserver.matchsimulator.Constants.MATCHES
import party.iobserver.matchsimulator.Constants.ME
import party.iobserver.matchsimulator.Constants.TEAMS
import party.iobserver.matchsimulator.R
import party.iobserver.matchsimulator.app
import party.iobserver.matchsimulator.model.Team
import party.iobserver.matchsimulator.util.AlertEnum
import party.iobserver.matchsimulator.util.showFullDialog


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

class TeamsAdapter(private val activity: Activity, var list: MutableList<Team> = mutableListOf()) : RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {
    val EDIT_TEAM = 3

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_teams, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.view.apply {
            teams_name.text = list[position].name
            teams_alias.text = list[position].alias
            teams_layout.setOnClickListener {
                val view = LayoutInflater.from(activity)
                        .inflate(R.layout.dialog_properties, null)
                context.alert {
                    positiveButton(R.string.dialog_positive) { }
                    title = list[position].name + " Properties"
                    customView = view
                }.show()
                val attackNum = view.dialog_properties_attack_num
                val attackBar = view.dialog_properties_attack_bar
                val defenceNum = view.dialog_properties_defence_num
                val defenceBar = view.dialog_properties_defence_bar

                attackNum.text = list[position].attack.toString()
                defenceNum.text = list[position].defence.toString()
                attackBar.progress = ((list[position].attack - 25) * 10).toInt()
                defenceBar.progress = ((list[position].defence - 65) * 10).toInt()
            }

            teams_editor.setOnClickListener {
                activity.startActivityForResult<TeamEditActivity>(EDIT_TEAM, "flag" to false, "id" to list[position].id)
            }

            teams_trash.setOnClickListener {
                // TODO need eliminate associations
                showFullDialog(activity, AlertEnum.WARNING, R.string.dialog_msg_delete) { _, _ ->
                    activity.app.appDatabase.teamDao().delete(list[position])
                }
            }
        }
    }

    class TeamsViewHolder(val view: View) : RecyclerView.ViewHolder(view)


}