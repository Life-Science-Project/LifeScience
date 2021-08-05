package com.jetbrains.life_science.controller.organisation

import com.jetbrains.life_science.user.organisation.service.OrganisationService
import com.jetbrains.life_science.user.organisation.view.OrganisationView
import com.jetbrains.life_science.user.organisation.view.OrganisationViewMapper
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/organisations")
class OrganisationController(
    val organisationService: OrganisationService,
    val viewMapper: OrganisationViewMapper
) {

    @Operation(summary = "Returns the count of all organisations on the portal")
    @GetMapping("/count")
    fun getOrganisationsCount(): Long {
        return organisationService.countAll()
    }

    @Operation(summary = "Returns all organisations")
    @GetMapping
    fun getAllOrganisations(): List<OrganisationView> {
        val organisations = organisationService.getAllOrganisations()
        return viewMapper.toViews(organisations)
    }
}
