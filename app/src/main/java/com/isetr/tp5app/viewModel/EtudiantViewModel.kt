package com.isetr.tp5app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isetr.tp5app.data.entity.Etudiant
import com.isetr.tp5app.repository.EtudiantRepository
import kotlinx.coroutines.launch

class EtudiantViewModel(private val repository: EtudiantRepository) : ViewModel() {

    // LiveData des étudiants récupérée directement du Repository (qui l'obtient du Room DAO)
    val allEtudiants: LiveData<List<Etudiant>> = repository.allEtudiants

    // Étudiant sélectionné (état de l'interface, pas stocké en base)
    private val _selectedEtudiant = MutableLiveData<Etudiant?>()
    val selectedEtudiant: LiveData<Etudiant?> = _selectedEtudiant

    private val _etudiantsSearchResults =
        MutableLiveData<List<Etudiant>>()

    val etudiantsSearchResults: LiveData<List<Etudiant>> =
        _etudiantsSearchResults

    fun selectionnerEtudiant(etudiant: Etudiant) {
        _selectedEtudiant.value = etudiant // Notifie le DetailEtudiantFragment
    }

    // Fonction d'insertion (nécessite viewModelScope et Coroutines)
    fun ajouterEtudiant(etudiant: Etudiant) = viewModelScope.launch {
        repository.insert(etudiant)
    }

    // Fonction de suppression (nécessite viewModelScope et Coroutines)
    fun delete(etudiant: Etudiant) = viewModelScope.launch {
        repository.delete(etudiant)
    }
    fun updateEtudiant(etudiant: Etudiant) = viewModelScope.launch {
        repository.update(etudiant)
    }

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            val searchQuery = "%$query%"
            val results = repository.searchEtudiants(searchQuery)
            _etudiantsSearchResults.postValue(results)
        }
    }

}
