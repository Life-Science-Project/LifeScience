package com.jetbrains.life_science.user.master.view

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.organisation.view.OrganisationView

class UserView(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String,
    val doctorDegree: DoctorDegree,
    val academicDegree: AcademicDegree,
    val organisations: List<OrganisationView>,
    val orcid: String?,
    val researchId: String?
)
