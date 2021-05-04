package com.jetbrains.life_science.user.organisation.controller

import com.jetbrains.life_science.user.organisation.service.OrganisationService
import com.jetbrains.life_science.user.organisation.view.OrganisationView
import com.jetbrains.life_science.user.organisation.view.OrganisationViewMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/organisations")
class OrganisationController(
    val organisationService: OrganisationService,
    val viewMapper: OrganisationViewMapper
) {

    @GetMapping
    fun getAllOrganisations(): List<OrganisationView> {
        val organisations = organisationService.getAllOrganisations()
        return viewMapper.toViews(organisations)
    }
}
