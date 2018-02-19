package party.iobserver.matchsimulator.database

import android.arch.lifecycle.LiveData
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
    fun all(): LiveData<MutableList<Team>>

    @Query("select * from teams where name = :name or alias = :alias")
    fun findByName(name: String, alias: String): MutableList<Team>

    @Query("select * from teams where id = :id")
    fun findById(id: Int): MutableList<Team>

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