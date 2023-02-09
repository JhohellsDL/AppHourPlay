package com.example.apphourplay

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.apphourplay.database.DuolingoDataBase
import com.example.apphourplay.database.DuolingoDataBaseDao
import com.example.apphourplay.database.DuolingoTime
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var sleepDao: DuolingoDataBaseDao
    private lateinit var db: DuolingoDataBase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, DuolingoDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        sleepDao = db.duolingoDataBaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = DuolingoTime()
        sleepDao.insert(night)
        val tonight = sleepDao.getDuolingoUser()
        assertEquals(tonight?.timeExtra, -1)
    }
}