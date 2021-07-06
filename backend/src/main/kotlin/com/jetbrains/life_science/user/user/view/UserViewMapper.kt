package com.jetbrains.life_science.user.user.view

import com.jetbrains.life_science.user.user.entity.User
import com.jetbrains.life_science.user.organisation.view.OrganisationViewMapper
import org.springframework.stereotype.Component

@Component
class UserViewMapper(
    val organisationViewMapper: OrganisationViewMapper
) {
    fun createView(user: User): UserView {
        return UserView(
            id = user.id,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            doctorDegree = user.doctorDegree,
            academicDegree = user.academicDegree,
            organisations = organisationViewMapper.toViews(user.organisations),
            orcid = user.orcid,
            researchId = user.researchId,
            roles = user.roles.map { it.name }
        )
    }
}
