package com.isetr.tp5app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.isetr.tp5app.data.dao.EtudiantDao
import com.isetr.tp5app.data.entity.Etudiant

@Database(entities = [Etudiant::class], version = 1, exportSchema = false)
abstract class EtudiantDatabase : RoomDatabase() {

    // Déclare l'interface DAO pour que Room la génère
    abstract fun etudiantDao(): EtudiantDao

    companion object {

        // 1. L'instance volatile (Singleton)
        @Volatile
        private var INSTANCE: EtudiantDatabase? = null

        // 2. La méthode pour obtenir l'instance
        fun getDatabase(context: Context): EtudiantDatabase {
            // Verrou : un seul thread à la fois crée la base de données
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EtudiantDatabase::class.java,
                    "etudiant_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}