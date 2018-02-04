package party.iobserver.matchsimulator

import android.app.Activity
import android.app.Application
import android.arch.persistence.room.Room
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import party.iobserver.matchsimulator.database.SimDatabase

/**
 * Created by ZeroGo on 2017/8/2.
 */
class SimApp : Application() {
    lateinit var appDatabase: SimDatabase
    lateinit var prefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        appDatabase = Room.databaseBuilder(this, SimDatabase::class.java, "sim.db").allowMainThreadQueries().build()
    }
}

val Activity.app: SimApp
    get() = this.application as SimApp

val Fragment.app: SimApp
    get() = this.activity?.application as SimApp