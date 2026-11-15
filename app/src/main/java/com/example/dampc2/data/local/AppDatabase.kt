package com.example.dampc2.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dampc2.asteroides.data.local.AsteroideDao
import com.example.dampc2.asteroides.data.local.AsteroideEntity
import com.example.dampc2.imagenes.data.local.ApodDao
import com.example.dampc2.imagenes.data.local.ApodEntity

/**
 * Base de datos común de la aplicación (Room / SQLite).
 * Contiene una tabla por opción: imagenes y asteroides.
 *
 * Uso: AppDatabase.getInstance(context).apodDao()
 */
@Database(
    entities = [ApodEntity::class, AsteroideEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun apodDao(): ApodDao

    abstract fun asteroideDao(): AsteroideDao

    companion object {
        private const val DATABASE_NAME = "diario_espacial.db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { instance = it }
            }
    }
}
