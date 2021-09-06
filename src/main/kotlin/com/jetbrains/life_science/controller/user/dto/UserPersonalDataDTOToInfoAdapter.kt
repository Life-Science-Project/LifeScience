package com.jetbrains.life_science.controller.user.dto

import com.jetbrains.life_science.exception.not_found.DegreeNotFoundException
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.data.service.UserPersonalDataInfo

class UserPersonalDataDTOToInfoAdapter(
    dto: UserPersonalDataDTO
) : UserPersonalDataInfo {

    private val academicDegreeMap: Map<String, AcademicDegree> = AcademicDegree.values().associateBy { it.name }

    override val doctorDegree = dto.doctorDegree

    override val academicDegree = academicDegreeMap[dto.academicDegree]
        ?: throw DegreeNotFoundException(dto.academicDegree)

    override val organisations = dto.organisations

    override val about = dto.about

    override val orcid = dto.orcid

    override val researchId = dto.researchId
}
