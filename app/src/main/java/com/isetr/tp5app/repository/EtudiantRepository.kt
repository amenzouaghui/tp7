package com.isetr.tp5app.repository

import androidx.lifecycle.LiveData
import com.isetr.tp5app.data.dao.EtudiantDao
import com.isetr.tp5app.data.entity.Etudiant

class EtudiantRepository(private val etudiantDao: EtudiantDao) {

    // La LiveData de la liste complète est récupérée directement du DAO
    val allEtudiants: LiveData<List<Etudiant>> = etudiantDao.getAllEtudiants()

    // Les opérations qui modifient les données sont suspendues (Coroutines)
    suspend fun insert(etudiant: Etudiant) {
        etudiantDao.insert(etudiant)
    }

    suspend fun delete(etudiant: Etudiant) {
        etudiantDao.delete(etudiant)
    }

    suspend fun deleteAll() {
        etudiantDao.deleteAll()
    }
    suspend fun update(etudiant: Etudiant) {
        etudiantDao.update(etudiant)
    }

    suspend fun searchEtudiants(searchQuery: String): List<Etudiant> {
        return etudiantDao.searchEtudiants(searchQuery)
    }
}