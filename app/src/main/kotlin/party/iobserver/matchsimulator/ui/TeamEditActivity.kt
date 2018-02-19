package party.iobserver.matchsimulator.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import com.jrummyapps.android.colorpicker.ColorPanelView
import com.jrummyapps.android.colorpicker.ColorPickerDialog
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener
import com.jrummyapps.android.colorpicker.ColorShape
import kotlinx.android.synthetic.main.activity_team_edit.*
import kotlinx.android.synthetic.main.app_bar.*
import party.iobserver.matchsimulator.R
import party.iobserver.matchsimulator.app
import party.iobserver.matchsimulator.model.Team

class TeamEditActivity : AppCompatActivity() {
    val colorPanelView: ColorPanelView by lazy { findViewById<ColorPanelView>(R.id.cpv_color_panel_view) }
    lateinit var team: Team
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_edit)

        val flag = intent.getBooleanExtra("flag", true)
        val position = intent.getIntExtra("position", 0)

        if (flag) {
            toolbar.title = getString(R.string.new_team)
            attack_num.text = 25.0.toString()
            defence_num.text = 65.0.toString()
            team = Team(0, "", "", 0, 25.0, 65.0)
        } else {
            team = app.appDatabase.teamDao().findById(position).first()
            toolbar.title = getString(R.string.edit_team) + " " + team.name

            name_text.setText(team.name)
            alias_text.setText(team.alias)
            attack_num.text = team.attack.toString()
            attack_bar.progress = ((team.attack - 25) * 10).toInt()
            defence_num.text = team.defence.toString()
            defence_bar.progress = ((team.defence - 65) * 10).toInt()
        }
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        colorPanelView.setOnClickListener {
            val dialog = ColorPickerDialog.newBuilder()
                    .setColor(team.color)
                    .setColorShape(ColorShape.CIRCLE)
                    .setShowAlphaSlider(true)
                    .create()
            dialog.setColorPickerDialogListener(object : ColorPickerDialogListener {
                override fun onColorSelected(dialogId: Int, color: Int) {
                    team.color = color
                    colorPanelView.color = color
                }

                override fun onDialogDismissed(dialogId: Int) = Unit
            })
            dialog.show(fragmentManager, "")
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        attack_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                attack_num.text = (progress.toDouble() / 10 + 25).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        defence_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                defence_num.text = (progress.toDouble() / 10 + 65).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }
}