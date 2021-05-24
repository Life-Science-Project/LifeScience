package com.jetbrains.life_science.user.master.dto

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree

data class UpdateDetailsDTO(

    val doctorDegree: DoctorDegree,

    val academicDegree: AcademicDegree,

    val organisations: List<String>,

    val orcid: String?,

    val researchId: String?
)
