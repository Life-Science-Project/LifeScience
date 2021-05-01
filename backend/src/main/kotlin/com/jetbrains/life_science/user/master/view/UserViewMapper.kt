package com.jetbrains.life_science.user.master.view

import com.jetbrains.life_science.user.master.entity.User
import org.springframework.stereotype.Component

@Component
class UserViewMapper {
    fun createView(user: User): UserView {
        return UserView(
            id = user.id,
            email = user.email,
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
