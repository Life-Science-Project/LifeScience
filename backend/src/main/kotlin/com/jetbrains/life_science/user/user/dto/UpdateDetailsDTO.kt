package com.jetbrains.life_science.user.user.dto

data class UpdateDetailsDTO(

    val doctorDegree: String,

    val academicDegree: String,

    val organisations: List<String>,

    val orcid: String?,

    val researchId: String?
)
