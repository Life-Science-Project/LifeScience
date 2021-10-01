package com.jetbrains.life_science.user.data.service

import com.jetbrains.life_science.user.degree.AcademicDegree

interface UserPersonalDataInfo {
    val doctorDegree: Boolean

    val academicDegree: AcademicDegree

    val organisations: List<Long>

    val orcid: String?

    val about: String?

    val researchId: String?
}
