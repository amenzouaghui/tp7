package com.isetr.tp5app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.isetr.tp5app.data.entity.Etudiant

@Dao
interface EtudiantDao {

    // 1. Ajouter ou mettre à jour un étudiant
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(etudiant: Etudiant)

    // 2. Supprimer un étudiant
    @Delete
    suspend fun delete(etudiant: Etudiant)

    // 3. Récupérer tous les étudiants triés par identifiant
    @Query("SELECT * FROM etudiant_table ORDER BY identifiant ASC")
    fun getAllEtudiants(): LiveData<List<Etudiant>>

    // 4. Supprimer tous les étudiants
    @Query("DELETE FROM etudiant_table")
    suspend fun deleteAll()

    @Update
    suspend fun update(etudiant: Etudiant)

    @Query("SELECT * FROM etudiant_table WHERE classe LIKE :searchQuery ORDER BY email ASC")
    suspend fun searchEtudiants(searchQuery: String): List<Etudiant>
}
