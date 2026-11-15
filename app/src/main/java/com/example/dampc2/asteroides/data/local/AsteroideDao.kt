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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(asteroides: List<AsteroideEntity>)
}
