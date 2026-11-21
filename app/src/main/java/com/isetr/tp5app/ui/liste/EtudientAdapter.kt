package com.isetr.tp5app.ui.liste

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isetr.tp5app.databinding.ItemEtudiantBinding
import com.isetr.tp5app.data.entity.Etudiant

class EtudiantAdapter(
    private var listeEtudiants: List<Etudiant>,
    private val onItemClick: (Etudiant) -> Unit,
    private val onItemLongClick: (Etudiant) -> Unit,
    private val onItemUpdate: (Etudiant) -> Unit
) : RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder>() {

    // 1. ViewHolder
    inner class EtudiantViewHolder(
        val binding: ItemEtudiantBinding
    ) : RecyclerView.ViewHolder(binding.root)

    // 2. Création de la vue
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EtudiantViewHolder {

        val binding = ItemEtudiantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return EtudiantViewHolder(binding)
    }

    // 3. Association des données avec les vues
    override fun onBindViewHolder(
        holder: EtudiantViewHolder,
        position: Int
    ) {
        val etudiant = listeEtudiants[position]

        holder.binding.tvIdentifiant.text =
            etudiant.identifiant.toString()

        holder.binding.tvEmail.text =
            etudiant.email

        holder.binding.tvClasse.text =
            etudiant.classe

        // 4. Gestion du clic
        holder.itemView.setOnClickListener {
            onItemClick(etudiant)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick(etudiant)
            true
        }
        holder.binding.btnUpdate.setOnClickListener { onItemUpdate(etudiant) }
    }

    // 5. Nombre d'éléments
    override fun getItemCount(): Int {
        return listeEtudiants.size
    }

    // 6. Méthode personnalisée pour mettre à jour la liste
    fun updateList(nouvelleListe: List<Etudiant>) {
        this.listeEtudiants = nouvelleListe
        notifyDataSetChanged()
    }
}
