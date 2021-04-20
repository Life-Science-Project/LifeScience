package com.jetbrains.life_science.user.details.entity

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.organisation.entity.Organisation

interface AddDetailsInfo {
    val doctorDegree: DoctorDegree

    val academicDegree: AcademicDegree

    val organisations: MutableList<Organisation>

    val orcid: String?

    val researchId: String?
}
