package com.jetbrains.life_science.user.data.dto

import com.jetbrains.life_science.exception.not_found.DegreeNotFoundException
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.data.service.UserPersonalDataInfo

class UserPersonalDataDTOToInfoAdapter(
    dto: UserPersonalDataDTO
) : UserPersonalDataInfo {

    private val doctorDegreeMap: Map<String, DoctorDegree> = DoctorDegree.values().associateBy { it.name }

    private val academicDegreeMap: Map<String, AcademicDegree> = AcademicDegree.values().associateBy { it.name }

    override val doctorDegree = doctorDegreeMap[dto.doctorDegree]
        ?: throw DegreeNotFoundException("Doctor degree ${dto.doctorDegree} doesn't exist.")

    override val academicDegree = academicDegreeMap[dto.academicDegree]
        ?: throw DegreeNotFoundException("Academic degree ${dto.academicDegree} doesn't exist.")

    override val organisations = dto.organisations

    override val orcid = dto.orcid

    override val researchId = dto.researchId
}