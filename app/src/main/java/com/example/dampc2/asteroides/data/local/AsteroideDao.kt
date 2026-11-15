package com.example.dampc2.asteroides.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/** DAO de la opción Asteroides. */
@Dao
interface AsteroideDao {

    /** Asteroides desde una fecha, los más recientes primero. */
    @Query("SELECT * FROM asteroides WHERE fechaAproximacion >= :desde ORDER BY fechaEpoch DESC")
    fun observarDesde(desde: String): Flow<List<AsteroideEntity>>

    @Query("SELECT * FROM asteroides WHERE id = :id")
    fun observarPorId(id: String): Flow<AsteroideEntity?>

    @Query("SELECT * FROM asteroides WHERE esFavorito = 1 ORDER BY fechaEpoch DESC")
    fun observarFavoritos(): Flow<List<AsteroideEntity>>

    @Query("UPDATE asteroides SET esFavorito = :esFavorito WHERE id = :id")
    suspend fun actualizarFavorito(id: String, esFavorito: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(asteroides: List<AsteroideEntity>)

    @Query("SELECT id FROM asteroides WHERE esFavorito = 1")
    suspend fun obtenerIdsFavoritos(): List<String>
}
