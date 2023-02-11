package com.example.apphourplay.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "duolingo_time_table")
data class DuolingoUserTime(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "name_user")
    var userName: String = "User",

    @ColumnInfo(name = "start_time_milli")
    var startTimeMilli: String = "${Calendar.getInstance().get(Calendar.HOUR_OF_DAY)}:${Calendar.getInstance().get(Calendar.MINUTE)}",

    @ColumnInfo(name = "star_time_milli_2")
    var startTimeMilli2: String = startTimeMilli,

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: String = startTimeMilli,

    @ColumnInfo(name = "stop_time_milli")
    var stopTimeMilli: String = startTimeMilli,

    @ColumnInfo(name = "time_state")
    var timeState: String = "off",

    @ColumnInfo(name = "time_remaining")
    var timeRemaining: Int = -1,

    @ColumnInfo(name = "time_extra")
    var timeExtra: Int = -1
)