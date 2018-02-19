package party.iobserver.matchsimulator.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import party.iobserver.matchsimulator.model.Expenses
import party.iobserver.matchsimulator.model.Team

@Database(entities = [(Expenses::class), (Team::class)], version = 2, exportSchema = false)
abstract class SimDatabase : RoomDatabase() {
    abstract fun expensesDao(): ExpensesDao
    abstract fun teamDao(): TeamDao
}