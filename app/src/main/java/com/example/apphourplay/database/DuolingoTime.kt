package com.example.apphourplay.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "duolingo_time_table")
data class DuolingoTime(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "name_user")
    val userName: String = "User",

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "star_time_milli_2")
    val startTimeMilli2: Long = startTimeMilli,

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = startTimeMilli,

    @ColumnInfo(name = "stop_time_milli")
    var stopTimeMilli: Long = startTimeMilli,

    @ColumnInfo(name = "time_extra")
    var timeExtra: Int = -1
)