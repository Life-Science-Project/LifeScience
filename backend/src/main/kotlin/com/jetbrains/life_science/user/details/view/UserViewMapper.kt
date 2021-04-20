package com.jetbrains.life_science.user.details.view

import com.jetbrains.life_science.user.details.entity.User
import org.springframework.stereotype.Component

@Component
class UserViewMapper {
    fun createView(user: User): UserView {
        return UserView(
            user.id,
            user.firstName,
            user.lastName,
            user.doctorDegree,
            user.academicDegree,
            user.organisations,
            user.orcid,
            user.researchId
        )
    }
}
