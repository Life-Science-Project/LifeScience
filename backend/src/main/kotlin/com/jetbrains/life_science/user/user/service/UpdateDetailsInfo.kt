package com.jetbrains.life_science.user.user.service

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree

interface UpdateDetailsInfo {
    val doctorDegree: DoctorDegree

    val academicDegree: AcademicDegree

    val organisations: List<String>

    val orcid: String?

    val researchId: String?
}
