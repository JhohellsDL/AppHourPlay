package com.example.apphourplay.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DuolingoDataBaseDao {

    @Insert
    fun insert(duolingoUser: DuolingoTime)

    @Update
    fun update(duolingoUser: DuolingoTime)

    @Query(value = "SELECT * from duolingo_time_table WHERE userId = :key")
    fun get(key: Long): DuolingoTime

    @Query(value = "DELETE FROM duolingo_time_table")
    fun clear()

    @Query(value = "SELECT * FROM duolingo_time_table ORDER BY userId DESC LIMIT 1")
    fun getDuolingoUser(): DuolingoTime?
}