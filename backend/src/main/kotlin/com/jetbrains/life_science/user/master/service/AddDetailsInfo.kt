package com.jetbrains.life_science.user.master.service

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.organisation.entity.Organisation

interface AddDetailsInfo {
    val doctorDegree: DoctorDegree

    val academicDegree: AcademicDegree

    val organisations: MutableList<Organisation>

    val orcid: String?

    val researchId: String?

    val user: User
}
