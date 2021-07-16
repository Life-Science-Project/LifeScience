package com.jetbrains.life_science.user.data.view

import com.jetbrains.life_science.user.data.entity.UserPersonalData
import com.jetbrains.life_science.user.organisation.view.OrganisationViewMapper
import org.springframework.stereotype.Component

@Component
class UserPersonalDataViewMapper(
    val organisationViewMapper: OrganisationViewMapper
) {
    fun createView(userData: UserPersonalData): UserPersonalDataView {
        return UserPersonalDataView(
            id = userData.id,
            email = userData.credentials.email,
            firstName = userData.firstName,
            lastName = userData.lastName,
            doctorDegree = userData.doctorDegree,
            academicDegree = userData.academicDegree,
            organisations = organisationViewMapper.toViews(userData.organisations),
            orcid = userData.orcid,
            researchId = userData.researchId,
            roles = userData.credentials.roles.map { it.name }
        )
    }
}
