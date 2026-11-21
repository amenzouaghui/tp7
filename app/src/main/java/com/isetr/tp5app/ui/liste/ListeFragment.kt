package com.isetr.tp5app.ui.liste

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.isetr.tp5app.R
import com.isetr.tp5app.EtudiantApplication
import com.isetr.tp5app.data.entity.Etudiant
import com.isetr.tp5app.databinding.DialogEditDetailsBinding
import com.isetr.tp5app.databinding.FragmentListeBinding
import com.isetr.tp5app.viewModel.EtudiantViewModel
import com.isetr.tp5app.viewModel.EtudiantViewModelFactory
class ListeFragment : Fragment() {
    private var rechercheActive = false
    private var _binding: FragmentListeBinding? = null
    private val binding get() = _binding!!

    private val etudiantViewModel: EtudiantViewModel by activityViewModels {

        val application =
            requireActivity().application as EtudiantApplication

        val repository = application.repository

        EtudiantViewModelFactory(repository)
    }

    private lateinit var adapter: EtudiantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListeBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(
            object : MenuProvider {

                override fun onCreateMenu(
                    menu: Menu,
                    menuInflater: MenuInflater
                ) {
                    menuInflater.inflate(R.menu.main, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return false
                }

            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        // 1. Initialiser le LayoutManager
        binding.rvEtudiants.layoutManager =
            LinearLayoutManager(requireContext())
        adapter = EtudiantAdapter(emptyList(),
            onItemClick = { etudiant ->
                etudiantViewModel.selectionnerEtudiant(etudiant)
                findNavController().navigate(R.id.action_liste_to_detail)
            },
            onItemLongClick = { etudiant -> confirmerSuppression(etudiant) },

            onItemUpdate = { etudiant -> afficherDialogModification(etudiant) }
        )
        // 2. Créer l'Adapter

        // 3. Associer l'Adapter au RecyclerView
        binding.rvEtudiants.adapter = adapter

        // 4. Observer le
        //
        //
        // LiveData
        // Observer de la liste complète
        etudiantViewModel.allEtudiants.observe(viewLifecycleOwner) {
            if (!rechercheActive) {
                adapter.updateList(it)
            }
        }

// Observer des résultats de recherche
        etudiantViewModel.etudiantsSearchResults.observe(viewLifecycleOwner) {
            if (rechercheActive) {
                adapter.updateList(it)
            }
        }


    }
    private fun confirmerSuppression(etudiant: Etudiant) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation")
            .setMessage("Voulez-vous vraiment supprimer ${etudiant.email} ?")
            .setPositiveButton("Supprimer") { _, _ ->
                etudiantViewModel.delete(etudiant)
                Toast.makeText(requireContext(), "Étudiant supprimé", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    private fun afficherDialogModification(etudiant: Etudiant) {
        val dialogBinding = DialogEditDetailsBinding.inflate(LayoutInflater.from(requireContext()))

        dialogBinding.etMail.setText(etudiant.email)
        dialogBinding.etClasse.setText(etudiant.classe)

        AlertDialog.Builder(requireContext())
            .setTitle("Modifier les détails de ${etudiant.identifiant}")
            .setView(dialogBinding.root)
            .setPositiveButton("SAUVEGARDER") { _, _ ->
                val etudiantModifie = etudiant.copy(
                    email = dialogBinding.etMail.text.toString(),
                    classe = dialogBinding.etClasse.text.toString()
                )
                etudiantViewModel.updateEtudiant(etudiantModifie)
            }
            .setNegativeButton("ANNULER", null)
            .show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
