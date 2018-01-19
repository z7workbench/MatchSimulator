package party.iobserver.matchsimulator

import android.app.Application
import org.litepal.LitePal

/**
 * Created by ZeroGo on 2017/8/2.
 */
class SimApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LitePal.initialize(this)
    }
}