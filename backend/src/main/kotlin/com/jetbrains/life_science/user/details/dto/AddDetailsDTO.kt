package com.jetbrains.life_science.user.details.dto

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.organisation.entity.Organisation

class AddDetailsDTO(

    val doctorDegree: DoctorDegree,

    val academicDegree: AcademicDegree,

    val organisations: MutableList<Organisation>,

    val orcid: String?,

    val researchId: String?
)
