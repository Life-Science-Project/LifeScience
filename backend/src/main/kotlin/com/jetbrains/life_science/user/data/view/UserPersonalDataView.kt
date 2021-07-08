package com.jetbrains.life_science.user.data.view

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.organisation.view.OrganisationView

data class UserPersonalDataView(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val doctorDegree: DoctorDegree,
    val academicDegree: AcademicDegree,
    val organisations: List<OrganisationView>,
    val orcid: String?,
    val researchId: String?,
    val roles: List<String>
)
