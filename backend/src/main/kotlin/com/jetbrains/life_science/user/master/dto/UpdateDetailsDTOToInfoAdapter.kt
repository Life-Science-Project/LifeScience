package com.jetbrains.life_science.user.master.dto

import com.jetbrains.life_science.user.master.service.UpdateDetailsInfo

class UpdateDetailsDTOToInfoAdapter(
    dto: UpdateDetailsDTO
) : UpdateDetailsInfo {

    override val doctorDegree = dto.doctorDegree

    override val academicDegree = dto.academicDegree

    override val organisations = dto.organisations

    override val orcid = dto.orcid

    override val researchId = dto.researchId
}
