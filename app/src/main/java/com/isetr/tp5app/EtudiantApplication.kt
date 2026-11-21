package com.isetr.tp5app

import android.app.Application
import com.isetr.tp5app.data.database.EtudiantDatabase
import com.isetr.tp5app.repository.EtudiantRepository

class EtudiantApplication : Application() {

    // 1. Initialisation de la base de données Room (le DAO est obtenu via cette instance)
    //    by lazy : créée une seule fois, au premier accès à 'database'
    private val database by lazy {
        EtudiantDatabase.getDatabase(this)
    }

    // 2. Initialisation du Repository (instance unique)
    //    Publique (val) pour être accessible par les Fragments
    val repository by lazy {
        EtudiantRepository(database.etudiantDao())
    }
}