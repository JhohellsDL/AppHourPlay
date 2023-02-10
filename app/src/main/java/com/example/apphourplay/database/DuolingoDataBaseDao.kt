package com.example.apphourplay.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DuolingoDataBaseDao {

    @Insert
    fun insert(duolingoUser: DuolingoUserTime)

    @Update
    fun update(duolingoUser: DuolingoUserTime)

    @Query(value = "SELECT * from duolingo_time_table WHERE userId = :key")
    fun get(key: Long): DuolingoUserTime

    @Query(value = "DELETE FROM duolingo_time_table")
    fun clear()

    @Query(value = "SELECT * FROM duolingo_time_table ORDER BY userId DESC")
    fun getAllDuoUsers(): LiveData<List<DuolingoUserTime>>

    @Query(value = "SELECT * FROM duolingo_time_table ORDER BY userId DESC LIMIT 1")
    fun getDuolingoUser(): DuolingoUserTime?
}