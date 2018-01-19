package party.iobserver.matchsimulator.util

import party.iobserver.matchsimulator.R

/**
 * Created by ZeroGo on 2017/8/2.
 */
enum class AlertEnum(val title: Int, val drawable: Int) {
    WARNING(R.string.dialog_warn, R.drawable.ic_warning_24dp),
    ERROR(R.string.dialog_error, R.drawable.ic_error_24dp),
    HINT(R.string.dialog_hint, R.drawable.ic_help_24dp)
}