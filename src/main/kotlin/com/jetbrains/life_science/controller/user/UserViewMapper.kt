package com.jetbrains.life_science.controller.user

import com.jetbrains.life_science.user.data.entity.UserPersonalData
import org.springframework.stereotype.Component

@Component
class UserViewMapper {

    fun toViewShortAll(users: List<UserPersonalData>): List<UserShortView> {
        return users.map { toViewShort(it) }
    }

    fun toViewShort(credentials: UserPersonalData): UserShortView {
        return UserShortView(
            id = credentials.id,
            fullName = credentials.firstName
        )
    }
}
