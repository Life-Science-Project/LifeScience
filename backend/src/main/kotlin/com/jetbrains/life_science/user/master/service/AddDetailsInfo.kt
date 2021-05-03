package com.jetbrains.life_science.user.master.service

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.organisation.entity.Organisation
import com.jetbrains.life_science.user.master.entity.User

interface AddDetailsInfo {
    val doctorDegree: DoctorDegree

    val academicDegree: AcademicDegree

    val organisations: MutableList<String>

    val orcid: String?

    val researchId: String?
}
