package com.jetbrains.life_science.user.data.dto

data class UserPersonalDataDTO(

    val doctorDegree: Boolean,

    val academicDegree: String,

    val organisations: List<String>,

    val orcid: String?,

    val researchId: String?
)
