package com.jetbrains.life_science.controller.user.view

import com.jetbrains.life_science.controller.favorite_group.view.FavoriteGroupViewMapper
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import com.jetbrains.life_science.user.organisation.view.OrganisationViewMapper
import org.springframework.stereotype.Component

@Component
class UserViewMapper(
    val organisationViewMapper: OrganisationViewMapper,
    val favoriteGroupViewMapper: FavoriteGroupViewMapper
) {

    fun toShortViewAll(users: List<UserPersonalData>): List<UserShortView> {
        return users.map { toShortView(it) }
    }

    fun toShortView(personalData: UserPersonalData): UserShortView {
        return UserShortView(
            id = personalData.credentials.id,
            fullName = personalData.firstName
        )
    }

    fun toPersonalDataView(userData: UserPersonalData): UserPersonalDataView {
        return UserPersonalDataView(
            firstName = userData.firstName,
            lastName = userData.lastName,
            organisations = organisationViewMapper.toViews(userData.organisations),
            orcid = userData.orcid ?: "",
            doctorDegree = userData.doctorDegree ?: false,
            academicDegree = userData.academicDegree,
            favoriteGroup = favoriteGroupViewMapper.toShortView(userData.favoriteGroup),
            about = userData.about ?: "",
            researchId = userData.researchId ?: "",
        )
    }

    fun toFullView(userData: UserPersonalData): UserFullView {
        val credentials = userData.credentials
        return UserFullView(
            id = credentials.id,
            email = credentials.email,
            roles = credentials.roles.toList().map { it.name },
            personalData = toPersonalDataView(userData)
        )
    }
}
