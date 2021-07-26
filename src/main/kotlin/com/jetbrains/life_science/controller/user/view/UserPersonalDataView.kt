package com.jetbrains.life_science.controller.user.view

import com.jetbrains.life_science.controller.favorite_group.view.FavoriteGroupShortView
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.organisation.view.OrganisationView

data class UserPersonalDataView(
    val firstName: String,
    val lastName: String,
    val organisations: List<OrganisationView>,
    val orcid: String,
    val doctorDegree: Boolean,
    val academicDegree: AcademicDegree,
    val favoriteGroup: FavoriteGroupShortView,
    val about: String,
    val researchId: String
)
