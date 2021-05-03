package com.jetbrains.life_science.user.master.dto

import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.master.service.AddDetailsInfo
import com.jetbrains.life_science.user.master.entity.User
import com.jetbrains.life_science.user.organisation.entity.Organisation

class AddDetailsDTOToInfoAdapter(
    private val dto: AddDetailsDTO,
    override val user: User
) : AddDetailsInfo {

    override val doctorDegree: DoctorDegree
        get() = dto.doctorDegree

    override val academicDegree: AcademicDegree
        get() = dto.academicDegree

    override val organisations: MutableList<String>
        get() = dto.organisations

    override val orcid: String?
        get() = dto.orcid

    override val researchId: String?
        get() = dto.researchId
}
