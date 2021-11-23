package com.dt.composeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dt.composeapp.database.dao.PersonDao
import com.dt.composeapp.database.model.PersonEntity

@Database(entities = [PersonEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object{

        private const val NOMBRE_DB = "SiianDB"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, NOMBRE_DB)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}