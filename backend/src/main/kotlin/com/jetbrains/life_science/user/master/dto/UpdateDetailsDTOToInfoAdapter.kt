package com.jetbrains.life_science.user.master.dto

import com.jetbrains.life_science.exception.not_found.DegreeNotFoundException
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.degree.DoctorDegree
import com.jetbrains.life_science.user.master.service.UpdateDetailsInfo

class UpdateDetailsDTOToInfoAdapter(
    dto: UpdateDetailsDTO
) : UpdateDetailsInfo {

    private val doctorDegreeMap = mapOf(
        "PhD" to DoctorDegree.PhD,
        "NONE" to DoctorDegree.NONE
    )

    private val academicDegreeMap = mapOf(
        "ASSOCIATE" to AcademicDegree.ASSOCIATE,
        "BACHELOR" to AcademicDegree.BACHELOR,
        "MASTER" to AcademicDegree.MASTER,
        "PROFESSIONAL" to AcademicDegree.PROFESSIONAL,
        "NONE" to AcademicDegree.NONE
    )

    override val doctorDegree = doctorDegreeMap[dto.doctorDegree]
        ?: throw DegreeNotFoundException("Doctor degree ${dto.doctorDegree} doesn't exist.")

    override val academicDegree = academicDegreeMap[dto.academicDegree]
        ?: throw DegreeNotFoundException("Academic degree ${dto.academicDegree} doesn't exist.")

    override val organisations = dto.organisations

    override val orcid = dto.orcid

    override val researchId = dto.researchId
}
