package party.iobserver.matchsimulator.util

import android.Manifest
import android.content.pm.ActivityInfo
import android.support.v4.app.Fragment
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import party.iobserver.matchsimulator.BuildConfig
import party.iobserver.matchsimulator.R
import permissions.dispatcher.NeedsPermission

object MatisseUtil {
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun selectFromFragment(from: Fragment, num: Int, forResult: Int) {
        Matisse.from(from)
                .choose(MimeType.allOf())
                .countable(true)
                .capture(true)
                .captureStrategy(CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".fileprovider"))
                .maxSelectable(num)
                .gridExpectedSize(from.resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .theme(R.style.AppTheme_Matisse)
                .imageEngine(GlideEngine())
                .forResult(forResult)
    }
}