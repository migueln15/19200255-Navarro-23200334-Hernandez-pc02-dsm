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

    @Query("SELECT * FROM imagenes WHERE isFavorite = 1 ORDER BY date DESC")
    fun observarFavoritos(): Flow<List<ApodEntity>>

    @Query("SELECT * FROM imagenes ORDER BY date DESC")
    suspend fun getApodImages(): List<ApodEntity>

    @Query("SELECT date FROM imagenes WHERE isFavorite = 1")
    suspend fun obtenerFechasFavoritas(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApodImages(images: List<ApodEntity>)

    @Query("UPDATE imagenes SET isFavorite = :isFavorite WHERE date = :date")
    suspend fun updateFavorite(date: String, isFavorite: Boolean)

    @Query("DELETE FROM imagenes")
    suspend fun clearApodImages()
}
