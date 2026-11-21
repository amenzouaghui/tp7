package com.isetr.tp5app.ui.ajout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.isetr.tp5app.R
import com.isetr.tp5app.databinding.FragmentAjoutBinding
import com.isetr.tp5app.data.entity.Etudiant


import com.isetr.tp5app.EtudiantApplication
import com.isetr.tp5app.viewModel.EtudiantViewModel
import com.isetr.tp5app.viewModel.EtudiantViewModelFactory
class AjoutFragment : Fragment() {

    private var _binding: FragmentAjoutBinding? = null
    private val binding get() = _binding!!

    // 1. Déclaration de l'instance du ViewModel partagé
    private val etudiantViewModel: EtudiantViewModel by activityViewModels {

        val application =
            requireActivity().application as EtudiantApplication

        val repository = application.repository

        EtudiantViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAjoutBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEnregistrer.setOnClickListener {
            val identifiantText = binding.etIdentifiant.text.toString()
            val email = binding.etEmail.text.toString()
            val classe = binding.etClasse.text.toString()

            if (identifiantText.isNotEmpty() && email.isNotEmpty() && classe.isNotEmpty()) {
                val identifiant = identifiantText.toInt()
                val nouvelEtudiant = Etudiant(identifiant, email, classe)

                // 2. Appel de la méthode sur l'INSTANCE viewModel
                etudiantViewModel.ajouterEtudiant(nouvelEtudiant)

                Toast.makeText(
                    requireContext(),
                    "Étudiant ajouté avec succès",
                    Toast.LENGTH_SHORT
                ).show()

                binding.etIdentifiant.text.clear()
                binding.etEmail.text.clear()
                binding.etClasse.text.clear()

                findNavController().navigate(R.id.action_ajout_to_liste)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Veuillez remplir tous les champs",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
