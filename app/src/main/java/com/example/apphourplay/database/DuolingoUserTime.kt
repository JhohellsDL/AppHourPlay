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
    val userName: String = "User",

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Int = Calendar.getInstance().get(Calendar.MINUTE),

    @ColumnInfo(name = "star_time_milli_2")
    val startTimeMilli2: Int = startTimeMilli,

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Int = startTimeMilli,

    @ColumnInfo(name = "stop_time_milli")
    var stopTimeMilli: Int = startTimeMilli,

    @ColumnInfo(name = "time_extra")
    var timeExtra: Int = -1
)