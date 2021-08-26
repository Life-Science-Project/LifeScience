package com.jetbrains.life_science.controller.user.dto

data class UserPersonalDataDTO(

    val doctorDegree: Boolean,

    val academicDegree: String,

    val organisations: List<Long>,

    val about: String?,

    val orcid: String?,

    val researchId: String?
)
