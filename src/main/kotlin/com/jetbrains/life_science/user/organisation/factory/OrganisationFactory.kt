package com.jetbrains.life_science.user.organisation.factory

import com.jetbrains.life_science.user.organisation.entity.Organisation
import org.springframework.stereotype.Component

@Component
class OrganisationFactory {

    fun create(name: String): Organisation = Organisation(0, name)
}
