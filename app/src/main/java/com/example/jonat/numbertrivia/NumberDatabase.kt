package com.example.jonat.numbertrivia

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Number::class], version = 7)
abstract class NumberDatabase : RoomDatabase() {

    abstract fun numberDAO(): NumberDAO

    companion object {
        @Volatile
        private var INSTANCE: NumberDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): NumberDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NumberDatabase::class.java,
                    "NumberDatabase"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(NumberDatabaseCallback(scope))
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class NumberDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {

                    }
                }
            }
        }


    }

}
