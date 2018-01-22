package party.iobserver.matchsimulator.util

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import party.iobserver.matchsimulator.R

/**
 * Created by ZeroGo on 2017/8/2.
 */
fun showSimpleDialog(context: Context, alertEnum: AlertEnum, msg: Int) {
    val dialog = AlertDialog.Builder(context)
            .setTitle(alertEnum.title)
            .setIcon(alertEnum.drawable)
            .setMessage(msg)
            .setPositiveButton(R.string.dialog_positive) { _, _ -> }
            .show()
    dialog.window.setWindowAnimations(R.style.dialogWindowAnim)
}

fun showFullDialog(context: Context, alertEnum: AlertEnum, msg: Int, listener: (dialog: DialogInterface, which: Int) -> Unit) {
    val dialog = AlertDialog.Builder(context)
            .setTitle(alertEnum.title)
            .setIcon(alertEnum.drawable)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(R.string.dialog_positive, listener)
            .setNegativeButton(R.string.dialog_negative) { _, _ -> }
            .show()
    dialog.window.setWindowAnimations(R.style.dialogWindowAnim)
}

