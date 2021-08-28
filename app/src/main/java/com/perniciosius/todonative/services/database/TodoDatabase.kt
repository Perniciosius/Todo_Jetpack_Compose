package com.perniciosius.todonative.services.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.perniciosius.todonative.models.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {
        private var instance: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            synchronized(this) {
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo"
                    ).fallbackToDestructiveMigration().build()
                }
                return instance as TodoDatabase
            }
        }
    }
}