package com.jetbrains.life_science.user.details.view

import com.jetbrains.life_science.user.details.entity.User
import org.springframework.stereotype.Component

@Component
class UserViewMapper {
    fun createView(user: User): UserView {
        return UserView(
            userDetailsId = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            doctorDegree = user.doctorDegree,
            academicDegree = user.academicDegree,
            organisations = user.organisations,
            orcid = user.orcid,
            researchId = user.researchId
        )
    }
}
