package com.example.dampc2.imagenes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO para la tabla de imágenes de APOD en Room.
 */
@Dao
interface ApodDao {

    @Query("SELECT * FROM imagenes ORDER BY date DESC")
    fun getApodImagesFlow(): Flow<List<ApodEntity>>

    @Query("SELECT * FROM imagenes WHERE date = :date LIMIT 1")
    fun getApodByDateFlow(date: String): Flow<ApodEntity?>

    @Query("SELECT * FROM imagenes ORDER BY date DESC")
    suspend fun getApodImages(): List<ApodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApodImages(images: List<ApodEntity>)

    @Query("DELETE FROM imagenes")
    suspend fun clearApodImages()
}
