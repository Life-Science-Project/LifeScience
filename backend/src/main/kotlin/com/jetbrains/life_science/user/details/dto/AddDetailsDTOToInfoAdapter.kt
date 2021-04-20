package com.jetbrains.life_science.user.details.dto

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.details.entity.AddDetailsInfo
import com.jetbrains.life_science.user.organisation.entity.Organisation

class AddDetailsDTOToInfoAdapter(
    private val dto: AddDetailsDTO,
) : AddDetailsInfo {

    override val doctorDegree: DoctorDegree
        get() = dto.doctorDegree

    override val academicDegree: AcademicDegree
        get() = dto.academicDegree

    override val organisations: MutableList<Organisation>
        get() = dto.organisations

    override val orcid: String?
        get() = dto.orcid

    override val researchId: String?
        get() = dto.researchId
}
