package com.example.apphourplay.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DuolingoTime::class], version = 1, exportSchema = false)
abstract class DuolingoDataBase: RoomDatabase() {

    abstract val duolingoDataBaseDao: DuolingoDataBaseDao

    companion object{

        @Volatile
        private var INSTANCE: DuolingoDataBase? = null

        fun getInstance(context: Context) : DuolingoDataBase {
             synchronized(this){
                 var instance = INSTANCE

                 if (instance == null){
                     instance = Room.databaseBuilder(
                         context.applicationContext,
                         DuolingoDataBase::class.java,
                         "duolingo_user_database"
                     )
                         .fallbackToDestructiveMigration()
                         .build()
                     INSTANCE = instance
                 }

                 return instance
             }
        }

    }

}