package com.isetr.tp5app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="etudiant_table")
data class Etudiant(
    @PrimaryKey val identifiant: Int,
    val email: String,
    val classe: String
)