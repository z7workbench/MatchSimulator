package party.iobserver.matchsimulator.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
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
import party.iobserver.matchsimulator.util.AlertEnum
import party.iobserver.matchsimulator.util.showFullDialog
import party.iobserver.matchsimulator.util.showSimpleDialog

class TeamEditActivity : AppCompatActivity() {
    val colorPanelView: ColorPanelView by lazy { findViewById<ColorPanelView>(R.id.cpv_color_panel_view) }
    lateinit var team: Team
    private val flag by lazy { intent.getBooleanExtra("flag", true) }
    var nameNotEmpty = false
    var aliasNotEmpty = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_edit)
        val position = intent.getIntExtra("id", 0)

        if (flag) {
            toolbar.title = getString(R.string.new_team)
            attack_num.text = 25.0.toString()
            defence_num.text = 65.0.toString()
            team = Team(0, "", "", 0, 25.0, 65.0)
        } else {
            team = app.appDatabase.teamDao().findById(position).first()
            toolbar.title = getString(R.string.edit_team) + " " + team.name

            name_text.isEnabled = false
            name_text.setText(team.name)
            alias_text.isEnabled = false
            alias_text.setText(team.alias)
            attack_num.text = team.attack.toString()
            attack_bar.progress = ((team.attack - 25) * 10).toInt()
            defence_num.text = team.defence.toString()
            defence_bar.progress = ((team.defence - 65) * 10).toInt()
            nameNotEmpty = true
            aliasNotEmpty = true
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
                val number = progress.toDouble() / 10 + 25
                attack_num.text = number.toString()
                team.attack = number
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        defence_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val number = progress.toDouble() / 10 + 65
                defence_num.text = number.toString()
                team.defence = number
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        name_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                team.name = s.toString()
                nameNotEmpty = s.isNotEmpty()
            }
        })

        alias_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                team.alias = s.toString()
                aliasNotEmpty = s.isNotEmpty()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_confirm -> {
                if (nameNotEmpty && aliasNotEmpty) {
                    if (flag) {
                        val boolean = app.appDatabase.teamDao().findByName(team.name, team.alias).isNotEmpty()
                        if (boolean) {
                            showSimpleDialog(this, AlertEnum.ERROR, R.string.dialog_msg_the_same_name)
                        } else {
                            app.appDatabase.teamDao().insert(team)
                            setResult(Activity.RESULT_OK)
                            finish()
                        }
                    } else {
                        showFullDialog(this, AlertEnum.WARNING, R.string.dialog_msg_confirm_edit) { _, _ ->
                            app.appDatabase.teamDao().update(team)
                            setResult(Activity.RESULT_OK)
                            finish()
                        }

                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}