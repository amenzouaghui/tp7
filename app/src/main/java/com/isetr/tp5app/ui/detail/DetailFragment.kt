package com.isetr.tp5app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.isetr.tp5app.databinding.FragmentDetailBinding

import com.isetr.tp5app.EtudiantApplication
import com.isetr.tp5app.viewModel.EtudiantViewModel
import com.isetr.tp5app.viewModel.EtudiantViewModelFactory
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentDetailBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etudiantViewModel.selectedEtudiant.observe(viewLifecycleOwner) { etudiant ->

            etudiant?.let {
                binding.tvIdentifiant.text = it.identifiant.toString()
                binding.tvEmail.text = it.email
                binding.tvClasse.text = it.classe
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}