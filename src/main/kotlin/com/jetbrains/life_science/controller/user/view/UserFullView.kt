package com.jetbrains.life_science.controller.user.view

data class UserFullView(
    val id: Long,
    val email: String,
    val roles: List<String>,
    val personalData: UserPersonalDataView?
)
