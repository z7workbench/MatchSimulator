package party.iobserver.matchsimulator.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity
import party.iobserver.matchsimulator.BuildConfig
import party.iobserver.matchsimulator.R

/**
 * Created by ZeroGo on 2017/8/20.
 */
class SplashActivity : AppCompatActivity() {
    private val SPLASH_DISPLAY_LENGTH : Long = 850

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(R.color.colorPrimaryLight)

        version.text = BuildConfig.VERSION_NAME

        Handler().postDelayed({
            startActivity<MainActivity>()
            this@SplashActivity.finish()
        }, SPLASH_DISPLAY_LENGTH)
    }
}