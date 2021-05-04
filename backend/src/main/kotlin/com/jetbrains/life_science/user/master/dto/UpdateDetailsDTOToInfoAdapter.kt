package com.jetbrains.life_science.user.master.dto

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.master.service.UpdateDetailsInfo

class UpdateDetailsDTOToInfoAdapter(
    private val dto: UpdateDetailsDTO
) : UpdateDetailsInfo {

    override val doctorDegree: DoctorDegree
        get() = dto.doctorDegree

    override val academicDegree: AcademicDegree
        get() = dto.academicDegree

    override val organisations: List<String>
        get() = dto.organisations

    override val orcid: String?
        get() = dto.orcid

    override val researchId: String?
        get() = dto.researchId
}
