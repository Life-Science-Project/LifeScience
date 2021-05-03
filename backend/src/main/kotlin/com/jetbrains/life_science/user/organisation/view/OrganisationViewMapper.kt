package com.jetbrains.life_science.user.organisation.view

import com.jetbrains.life_science.user.organisation.entity.Organisation
import org.springframework.stereotype.Component

@Component
class OrganisationViewMapper {

    fun toView(organisation: Organisation):
        OrganisationView = OrganisationView(organisation.id, organisation.name)

    fun toViews(organisations: List<Organisation>): List<OrganisationView> {
        return organisations.map { toView(it) }
    }
}
