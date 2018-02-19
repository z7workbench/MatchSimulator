package party.iobserver.matchsimulator.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zhihu.matisse.Matisse
import kotlinx.android.synthetic.main.fragment_me.*
import kotlinx.android.synthetic.main.fragment_me.view.*
import party.iobserver.matchsimulator.GlideApp
import party.iobserver.matchsimulator.R
import party.iobserver.matchsimulator.app
import party.iobserver.matchsimulator.util.GlideEngine
import party.iobserver.matchsimulator.util.MatisseUtil


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
    private val MATISSE_CODE = 1
    private val ASK_PERMISSION = 2
    private var uris = listOf<Uri>()
    private lateinit var rootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_me, container, false)
        val avatar = app.prefs.getString("avatar", null)
        fragmentManager!!.beginTransaction()
                .replace(R.id.content, SettingsFragment())
                .commit()

        rootView.apply {
            hideAndShow.setOnClickListener {
                hideOrNot(true)
            }
            GlideApp.with(this)
                    .load(avatar)
                    .apply(GlideEngine.options)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatar_view)
        }
        hideOrNot(false)

        rootView.avatar_view.setOnClickListener {
            val hasPermission = activity!!.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity!!.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), ASK_PERMISSION)
                return@setOnClickListener
            }
            MatisseUtil.selectFromFragment(this, 1, MATISSE_CODE)
        }
        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == MATISSE_CODE) {
            uris = Matisse.obtainResult(data)
            GlideApp.with(this)
                    .load(uris[0].toString())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(GlideEngine.options)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatar_view)
            app.prefs.edit().putString("avatar", uris[0].toString()).apply()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == ASK_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            MatisseUtil.selectFromFragment(this, 1, MATISSE_CODE)
        } else super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun hideOrNot(edit: Boolean) {
        val hide = app.prefs.getBoolean("hide", true)
        if ((!hide && edit) || (hide && !edit)) {
            rootView.apply {
                hideAndShow.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_show))
                cash.setText(getString(R.string.me_cash) + "****")
            }
            if (edit) {
                val editor = app.prefs.edit()
                editor.putBoolean("hide", true)
                editor.apply()
            }

        } else {
            val cashInt: Int = app.prefs.getInt("cash", 0)
            rootView.apply {
                hideAndShow.setImageDrawable(ContextCompat.getDrawable(context!!, R.drawable.ic_hide))
                cash.setText(getString(R.string.me_cash) + cashInt)
            }
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