package party.iobserver.matchsimulator.model

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
                var alias: String,
                var attack: Double,
                var defence: Double
)