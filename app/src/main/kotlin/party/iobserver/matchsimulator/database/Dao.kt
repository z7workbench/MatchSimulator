package party.iobserver.matchsimulator.database

import android.arch.persistence.room.*
import party.iobserver.matchsimulator.model.Expenses
import party.iobserver.matchsimulator.model.Team

@Dao
interface ExpensesDao {
    @Query("select * from expenses")
    fun all(): MutableList<Expenses>

    @Insert
    fun insert(expenses: Expenses)

    @Insert
    fun insertAll(vararg expenses: Expenses)
}

@Dao
interface TeamDao {
    @Query("select * from teams")
    fun all(): MutableList<Team>

    @Insert
    fun insert(team: Team)

    @Insert
    fun insertAll(vararg teams: Team)

    @Update
    fun update(team: Team)

    @Delete
    fun delete(team: Team)

    @Query("delete from teams")
    fun deleteAll()
}