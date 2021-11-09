package com.hs_worms.android.roomrepositoryexample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hs_worms.android.roomrepositoryexample.model.Element
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Element::class), version = 1, exportSchema = false)
@TypeConverters(ElementTypeConverter::class)
public abstract class ElementDB : RoomDatabase() {

    abstract fun elementDao(): ElementDao

    companion object {

        @Volatile
        private var INSTANCE: ElementDB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ElementDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ElementDB::class.java,
                    "element_database"
                ).addCallback(ElementDatabaseCallback(scope)).build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class ElementDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            ElementDB.INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.elementDao())
                }
            }
        }

        suspend fun populateDatabase(elementDao: ElementDao) {
            elementDao.deleteAll()

            for (i in 0..20) {
                val e = Element(number =  i, title = "Dies ist Element Nr. " + i.toString())
                elementDao.addElement(e)
            }
        }
    }

}

