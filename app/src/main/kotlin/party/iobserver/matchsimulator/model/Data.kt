package party.iobserver.matchsimulator.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expenses(@PrimaryKey(autoGenerate = true) var id: Int,
                    var time: Long,
                    var description: String,
                    var earned: Int
)

@Entity(tableName = "teams")
data class Team(@PrimaryKey(autoGenerate = true) var id: Int,
                var name: String,
                @ColumnInfo(index = true) var alias: String,
                var color: Int,
                var attack: Double,
                var defence: Double
)