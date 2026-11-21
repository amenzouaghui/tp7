package com.isetr.tp5app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.isetr.tp5app.repository.EtudiantRepository

// 1. Déclaration de la classe Factory. Elle implémente l'interface requise par Android.
//    Elle prend en paramètre le Repository qu'elle devra injecter.
class EtudiantViewModelFactory(private val repository: EtudiantRepository) : ViewModelProvider.Factory {

    // 2. Surcharge de la méthode 'create'. C'est le point d'entrée pour la construction du ViewModel.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // 3. Vérification de type : on s'assure que la classe demandée est bien EtudiantViewModel.
        if (modelClass.isAssignableFrom(EtudiantViewModel::class.java)) {

            // 4. Création du ViewModel : on injecte le 'repository' stocké dans le constructeur.
            //    'as T' caste l'objet vers le type générique attendu par le système.
            @Suppress("UNCHECKED_CAST")
            return EtudiantViewModel(repository) as T
        }

        // 5. Gestion d'erreur : si une classe de ViewModel inconnue est demandée, on lève une exception.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}